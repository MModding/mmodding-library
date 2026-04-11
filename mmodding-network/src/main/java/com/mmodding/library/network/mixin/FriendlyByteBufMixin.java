package com.mmodding.library.network.mixin;

import com.mmodding.library.java.api.container.Typed;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.MixedMap;
import com.mmodding.library.network.api.FriendlyByteBufExtension;
import com.mmodding.library.network.impl.NetworkHandlersImpl;
import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import net.minecraft.ResourceLocationException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

@Mixin(FriendlyByteBuf.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public abstract class FriendlyByteBufMixin implements FriendlyByteBufExtension {

	@Shadow
	public abstract <T, C extends Collection<T>> C readCollection(IntFunction<C> collectionFactory, FriendlyByteBuf.Reader<T> entryReader);

	@Shadow
	public abstract ResourceLocation readResourceLocation();

	@Shadow
	public abstract <T> void writeCollection(Collection<T> collection, FriendlyByteBuf.Writer<T> entryWriter);

	@Shadow
	public abstract FriendlyByteBuf writeResourceLocation(ResourceLocation id);

	@Shadow
	public abstract ByteBuf copy();

	@Shadow
	public abstract <K, V, M extends Map<K, V>> M readMap(IntFunction<M> mapFactory, FriendlyByteBuf.Reader<K> keyReader, FriendlyByteBuf.Reader<V> valueReader);

	@Shadow
	public abstract <K, V> void writeMap(Map<K, V> map, FriendlyByteBuf.Writer<K> keyWriter, FriendlyByteBuf.Writer<V> valueWriter);

	@Override
	public Optional<Class<?>> peekNextType() {
		FriendlyByteBuf copied = new FriendlyByteBuf(this.copy());
		ResourceLocation identifier;
		try {
			identifier = copied.readResourceLocation();
			copied.release();
		}
		catch (ResourceLocationException error) {
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
		return this.handlingReader(type).apply((FriendlyByteBuf) (Object) this);
	}

	@Override
	public <T> void writeByHandling(T value) {
		this.handlingWriter(value.getClass()).accept((FriendlyByteBuf) (Object) this, value);
	}

	@Override
	public <T> Optional<T> readOptionalByHandling(Class<T> type) {
		return this.handlingReader(type).asOptional().apply((FriendlyByteBuf) (Object) this);
	}

	@Override
	public <T> void writeOptionalByHandling(T value) {
		this.handlingWriter(value.getClass()).asOptional().accept((FriendlyByteBuf) (Object) this, Optional.ofNullable(value));
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
	public <T> MixedMap<T> readMixedMap(FriendlyByteBuf.Reader<T> entryReader) {
		return this.readMap(
			i -> MixedMap.create(),
			entryReader,
			current -> Typed.of(current.readByHandling(current.peekNextType().orElseThrow()))
		);
	}

	@Override
	public <T> void writeMixedMap(MixedMap<T> map, FriendlyByteBuf.Writer<T> entryWriter) {
		this.writeMap(
			map,
			entryWriter,
			(current, typed) -> current.writeByHandling(typed.getValue())
		);
	}

	@Unique
	@SuppressWarnings("unchecked")
	private <T> FriendlyByteBuf.Reader<T> handlingReader(Class<T> type) {
		if (this.peekNextType().isEmpty()) {
			throw new IllegalStateException("Next value is not network-handled!");
		}
		else if (this.peekNextType().get() != type) {
			throw new IllegalArgumentException("Next value is not an instance of " + type + "!");
		}
		return (FriendlyByteBuf.Reader<T>) NetworkHandlersImpl.HANDLERS.getFirstValue(this.readResourceLocation());
	}

	@Unique
	@SuppressWarnings("unchecked")
	private <T> FriendlyByteBuf.Writer<T> handlingWriter(Class<?> type) {
		if (!NetworkHandlersImpl.IDS.containsKey(type)) {
			throw new IllegalArgumentException("Value cannot be network-handled as " + type + " does not have any registered handling factories!");
		}
		else {
			this.writeResourceLocation(NetworkHandlersImpl.IDS.get(type));
			return (FriendlyByteBuf.Writer<T>) NetworkHandlersImpl.HANDLERS.getSecondValue(NetworkHandlersImpl.IDS.get(type));
		}
	}
}
