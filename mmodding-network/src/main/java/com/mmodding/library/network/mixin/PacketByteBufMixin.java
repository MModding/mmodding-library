package com.mmodding.library.network.mixin;

import com.mmodding.library.java.api.container.Typed;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.MixedMap;
import com.mmodding.library.network.api.PacketByteBufExtension;
import com.mmodding.library.network.impl.NetworkHandlersImpl;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;

@Mixin(PacketByteBuf.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public abstract class PacketByteBufMixin implements PacketByteBufExtension {

	@Shadow
	public abstract <T, C extends Collection<T>> C readCollection(IntFunction<C> collectionFactory, PacketByteBuf.Reader<T> entryReader);

	@Shadow
	public abstract Identifier readIdentifier();

	@Shadow
	public abstract <T> void writeCollection(Collection<T> collection, PacketByteBuf.Writer<T> entryWriter);

	@Shadow
	public abstract PacketByteBuf writeIdentifier(Identifier id);

	@Shadow
	public abstract ByteBuf copy();

	@Shadow
	public abstract <K, V, M extends Map<K, V>> M readMap(IntFunction<M> mapFactory, PacketByteBuf.Reader<K> keyReader, PacketByteBuf.Reader<V> valueReader);

	@Shadow
	public abstract <K, V> void writeMap(Map<K, V> map, PacketByteBuf.Writer<K> keyWriter, PacketByteBuf.Writer<V> valueWriter);

	@Override
	public Optional<Class<?>> peekNextType() {
		PacketByteBuf copied = new PacketByteBuf(this.copy());
		Identifier identifier;
		try {
			identifier = copied.readIdentifier();
			copied.release();
		}
		catch (InvalidIdentifierException error) {
			return Optional.empty();
		}
		if (NetworkHandlersImpl.TYPES.containsKey(identifier)) {
			return Optional.of(NetworkHandlersImpl.TYPES.get(identifier));
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public <T> T readByHandling(Class<T> type) {
		return this.handlingReader(type).apply((PacketByteBuf) (Object) this);
	}

	@Override
	public <T> void writeByHandling(T value) {
		this.handlingWriter(value.getClass()).accept((PacketByteBuf) (Object) this, value);
	}

	@Override
	public <T> Optional<T> readOptionalByHandling(Class<T> type) {
		return this.handlingReader(type).asOptional().apply((PacketByteBuf) (Object) this);
	}

	@Override
	public <T> void writeOptionalByHandling(T value) {
		this.handlingWriter(value.getClass()).asOptional().accept((PacketByteBuf) (Object) this, Optional.ofNullable(value));
	}

	@Override
	public MixedList readMixedList() {
		return this.readCollection(
			i -> MixedList.create(),
			current -> Typed.of(current.readByHandling(current.peekNextType().orElseThrow()))
		);
	}

	@Override
	public void writeMixedList(MixedList list) {
		this.writeCollection(
			list,
			(current, typed) -> current.writeByHandling(typed.getValue())
		);
	}

	@Override
	public <T> MixedMap<T> readMixedMap(PacketByteBuf.Reader<T> entryReader) {
		return this.readMap(
			i -> MixedMap.create(),
			entryReader,
			current -> Typed.of(current.readByHandling(current.peekNextType().orElseThrow()))
		);
	}

	@Override
	public <T> void writeMixedMap(MixedMap<T> map, PacketByteBuf.Writer<T> entryWriter) {
		this.writeMap(
			map,
			entryWriter,
			(current, typed) -> current.writeByHandling(typed.getValue())
		);
	}

	@Unique
	@SuppressWarnings("unchecked")
	private <T> PacketByteBuf.Reader<T> handlingReader(Class<T> type) {
		if (this.peekNextType().isEmpty()) {
			throw new IllegalStateException("Next value is not network-handled!");
		}
		else if (this.peekNextType().get() != type) {
			throw new IllegalArgumentException("Next value is not an instance of " + type + "!");
		}
		return (PacketByteBuf.Reader<T>) NetworkHandlersImpl.HANDLERS.getFirstValue(this.readIdentifier());
	}

	@Unique
	@SuppressWarnings("unchecked")
	private <T> PacketByteBuf.Writer<T> handlingWriter(Class<?> type) {
		if (!NetworkHandlersImpl.IDS.containsKey(type)) {
			throw new IllegalArgumentException("Value cannot be network-handled as " + type + " does not have any registered handling factories!");
		}
		else {
			this.writeIdentifier(NetworkHandlersImpl.IDS.get(type));
			return (PacketByteBuf.Writer<T>) NetworkHandlersImpl.HANDLERS.getSecondValue(NetworkHandlersImpl.IDS.get(type));
		}
	}
}
