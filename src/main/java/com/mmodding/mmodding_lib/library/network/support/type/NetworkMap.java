package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import com.mmodding.mmodding_lib.library.utils.*;
import com.mmodding.mmodding_lib.mixin.accessors.NbtByteArrayAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.NbtIntArrayAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.NbtLongArrayAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.List;
import java.util.UUID;

public class NetworkMap implements NetworkSupport {

	private final MixedMap<Identifier> entries = new MixedHashMap<>();

	private <T> NetworkPrimitive<T> nullable(NetworkPrimitive<T> primitive) {
		return ObjectUtils.ifNullMakeDefault(primitive, NetworkPrimitive::empty);
	}

	public <T extends NetworkSupport> T get(Identifier key, Class<T> type) {
		return this.entries.get(key, type);
	}

	public byte getByte(Identifier key) {
		return this.nullable(this.get(key, NetworkByte.class)).getValue();
	}

	public short getShort(Identifier key) {
		return this.nullable(this.get(key, NetworkShort.class)).getValue();
	}

	public int getInt(Identifier key) {
		return this.nullable(this.get(key, NetworkInteger.class)).getValue();
	}

	public long getLong(Identifier key) {
		return this.nullable(this.get(key, NetworkLong.class)).getValue();
	}

	public Identifier getIdentifier(Identifier key) {
		return this.nullable(this.get(key, NetworkIdentifier.class)).getValue();
	}

	public UUID getUuid(Identifier key) {
		return this.nullable(this.get(key, NetworkUUID.class)).getValue();
	}

	public float getFloat(Identifier key) {
		return this.nullable(this.get(key, NetworkFloat.class)).getValue();
	}

	public double getDouble(Identifier key) {
		return this.nullable(this.get(key, NetworkDouble.class)).getValue();
	}

	public String getString(Identifier key) {
		return this.nullable(this.get(key, NetworkString.class)).getValue();
	}

	public byte[] getByteArray(Identifier key) {
		return this.nullable(this.get(key, NetworkByteArray.class)).getValue();
	}

	public int[] getIntArray(Identifier key) {
		return this.nullable(this.get(key, NetworkIntegerArray.class)).getValue();
	}

	public long[] getLongArray(Identifier key) {
		return this.nullable(this.get(key, NetworkLongArray.class)).getValue();
	}

	public boolean getBoolean(Identifier key) {
		return this.nullable(this.get(key, NetworkBoolean.class)).getValue();
	}

	public ItemStack getItemStack(Identifier key) {
		return this.nullable(this.get(key, NetworkItemStack.class)).getValue();
	}

	public <T extends NetworkSupport> void put(Identifier key, Class<T> type, T value) {
		this.entries.put(key, type, value);
	}

	public void putByte(Identifier key, byte value) {
		this.put(key, NetworkByte.class, NetworkByte.of(value));
	}

	public void putShort(Identifier key, short value) {
		this.put(key, NetworkShort.class, NetworkShort.of(value));
	}

	public void putInt(Identifier key, int value) {
		this.put(key, NetworkInteger.class, NetworkInteger.of(value));
	}

	public void putLong(Identifier key, long value) {
		this.put(key, NetworkLong.class, NetworkLong.of(value));
	}

	public void putIdentifier(Identifier key, Identifier value) {
		this.put(key, NetworkIdentifier.class, NetworkIdentifier.of(value));
	}

	public void putUuid(Identifier key, UUID value) {
		this.put(key, NetworkUUID.class, NetworkUUID.of(value));
	}

	public void putFloat(Identifier key, float value) {
		this.put(key, NetworkFloat.class, NetworkFloat.of(value));
	}

	public void putDouble(Identifier key, double value) {
		this.put(key, NetworkDouble.class, NetworkDouble.of(value));
	}

	public void putString(Identifier key, String value) {
		this.put(key, NetworkString.class, NetworkString.of(value));
	}

	public void putByteArray(Identifier key, byte[] value) {
		this.put(key, NetworkByteArray.class, NetworkByteArray.of(value));
	}

	public void putByteArray(Identifier key, List<Byte> value) {
		this.putByteArray(key, NbtByteArrayAccessor.invokeToArray(value));
	}

	public void putIntArray(Identifier key, int[] value) {
		this.put(key, NetworkIntegerArray.class, NetworkIntegerArray.of(value));
	}

	public void putIntArray(Identifier key, List<Integer> value) {
		this.putIntArray(key, NbtIntArrayAccessor.invokeToArray(value));
	}

	public void putLongArray(Identifier key, long[] value) {
		this.put(key, NetworkLongArray.class, NetworkLongArray.of(value));
	}

	public void putLongArray(Identifier key, List<Long> value) {
		this.putLongArray(key, NbtLongArrayAccessor.invokeToArray(value));
	}

	public void putBoolean(Identifier key, boolean value) {
		this.put(key, NetworkBoolean.class, NetworkBoolean.of(value));
	}

	public void putItemStack(Identifier key, ItemStack value) {
		this.put(key, NetworkItemStack.class, NetworkItemStack.of(value));
	}

	public void forEach(TriConsumer<Identifier, Class<? extends NetworkSupport>, ? extends NetworkSupport> action) {
		this.entries.forEach(action);
	}

	public static NetworkMap read(PacketByteBuf buf) {
		NetworkMap list = new NetworkMap();
		list.entries.putAll(buf.readMap(PacketByteBuf::readIdentifier, current -> TypedObject.of(NetworkSupport.getType(current), NetworkSupport.readComplete(current))));
		return list;
	}

	@Override
	public void write(PacketByteBuf buf) {
		buf.writeMap(this.entries, PacketByteBuf::writeIdentifier, (current, typed) -> ((NetworkSupport) typed.getValue()).writeComplete(current));
	}

	public MixedMap<Identifier> getEntries() {
		return new MixedHashMap<>(this.entries);
	}

	static {
		NetworkSupport.register(new MModdingIdentifier("map"), NetworkMap.class, NetworkMap::read);
	}
}
