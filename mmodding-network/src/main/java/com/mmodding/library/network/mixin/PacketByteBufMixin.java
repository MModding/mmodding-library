package com.mmodding.library.network.mixin;

import com.mmodding.library.network.api.PacketByteBufExtension;
import com.mmodding.library.network.impl.NetworkHandlersImpl;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(PacketByteBuf.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public abstract class PacketByteBufMixin implements PacketByteBufExtension {

	@Shadow
	public abstract Identifier readIdentifier();

	@Shadow
	public abstract ByteBuf copy();

	@Shadow
	public abstract PacketByteBuf writeIdentifier(Identifier id);

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
	public <T> void writeByHandling(Class<T> type, T value) {
		this.handlingWriter(type).accept((PacketByteBuf) (Object) this, value);
	}

	@Override
	public <T> Optional<T> readOptionalByHandling(Class<T> type) {
		return this.handlingReader(type).asOptional().apply((PacketByteBuf) (Object) this);
	}

	@Override
	public <T> void writeOptionalByHandling(Class<T> type, T value) {
		this.handlingWriter(type).asOptional().accept((PacketByteBuf) (Object) this, Optional.ofNullable(value));
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
	private <T> PacketByteBuf.Writer<T> handlingWriter(Class<T> type) {
		if (!NetworkHandlersImpl.IDS.containsKey(type)) {
			throw new IllegalArgumentException("Value cannot be network-handled as " + type + " does not have any registered handling factories!");
		}
		else {
			this.writeIdentifier(NetworkHandlersImpl.IDS.get(type));
			return (PacketByteBuf.Writer<T>) NetworkHandlersImpl.HANDLERS.getSecondValue(NetworkHandlersImpl.IDS.get(type));
		}
	}
}
