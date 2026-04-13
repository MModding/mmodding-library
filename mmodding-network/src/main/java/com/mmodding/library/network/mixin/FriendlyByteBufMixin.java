package com.mmodding.library.network.mixin;

import com.mmodding.library.java.api.container.Typed;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.java.api.map.MixedMap;
import com.mmodding.library.network.api.FriendlyByteBufExtension;
import com.mmodding.library.network.impl.NetworkHandlersImpl;
import io.netty.buffer.ByteBuf;
import net.minecraft.IdentifierException;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import net.minecraft.network.FriendlyByteBuf;

@Mixin(FriendlyByteBuf.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public abstract class FriendlyByteBufMixin implements FriendlyByteBufExtension {

	@Shadow
	public abstract <T, C extends Collection<T>> C readCollection(IntFunction<C> ctor, StreamDecoder<? super FriendlyByteBuf, T> elementDecoder);

	@Shadow
	public abstract Identifier readIdentifier();

	@Shadow
	public abstract <T> void writeCollection(Collection<T> collection, StreamEncoder<? super FriendlyByteBuf, T> encoder);

	@Shadow
	public abstract FriendlyByteBuf writeIdentifier(Identifier identifier);

	@Shadow
	public abstract ByteBuf copy();

	@Shadow
	public abstract <K, V, M extends Map<K, V>> M readMap(IntFunction<M> ctor, StreamDecoder<? super FriendlyByteBuf, K> keyDecoder, StreamDecoder<? super FriendlyByteBuf, V> valueDecoder);

	@Shadow
	public abstract <K, V> void writeMap(Map<K, V> map, StreamEncoder<? super FriendlyByteBuf, K> keyEncoder, StreamEncoder<? super FriendlyByteBuf, V> valueEncoder);

	@Override
	public Optional<Class<?>> peekNextType() {
		FriendlyByteBuf copied = new FriendlyByteBuf(this.copy());
		Identifier identifier;
		try {
			identifier = copied.readIdentifier();
			copied.release();
		}
		catch (IdentifierException error) {
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
		return this.handlingReader(type).decode((FriendlyByteBuf) (Object) this);
	}

	@Override
	public <T> void writeByHandling(T value) {
		this.handlingWriter(value.getClass()).encode((FriendlyByteBuf) (Object) this, value);
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
	public <T> MixedMap<T> readMixedMap(StreamDecoder<? super FriendlyByteBuf, T> entryReader) {
		return this.readMap(
			i -> MixedMap.create(),
			entryReader,
			current -> Typed.of(current.readByHandling(current.peekNextType().orElseThrow()))
		);
	}

	@Override
	public <T> void writeMixedMap(MixedMap<T> map, StreamEncoder<? super FriendlyByteBuf, T> entryWriter) {
		this.writeMap(
			map,
			entryWriter,
			(current, typed) -> current.writeByHandling(typed.getValue())
		);
	}

	@Unique
	@SuppressWarnings("unchecked")
	private <T> StreamDecoder<? super FriendlyByteBuf, T> handlingReader(Class<T> type) {
		if (this.peekNextType().isEmpty()) {
			throw new IllegalStateException("Next value is not network-handled!");
		}
		else if (this.peekNextType().get() != type) {
			throw new IllegalArgumentException("Next value is not an instance of " + type + "!");
		}
		return (StreamDecoder<? super FriendlyByteBuf, T>) NetworkHandlersImpl.HANDLERS.getFirstValue(this.readIdentifier());
	}

	@Unique
	@SuppressWarnings("unchecked")
	private <T> StreamEncoder<? super FriendlyByteBuf, T> handlingWriter(Class<?> type) {
		if (!NetworkHandlersImpl.IDS.containsKey(type)) {
			throw new IllegalArgumentException("Value cannot be network-handled as " + type + " does not have any registered handling factories!");
		}
		else {
			this.writeIdentifier(NetworkHandlersImpl.IDS.get(type));
			return (StreamEncoder<? super FriendlyByteBuf, T>) NetworkHandlersImpl.HANDLERS.getSecondValue(NetworkHandlersImpl.IDS.get(type));
		}
	}
}
