package com.mmodding.mmodding_lib.library.network.support.type;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import com.mmodding.mmodding_lib.library.utils.*;
import com.mmodding.mmodding_lib.mixin.accessors.NbtByteArrayAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.NbtIntArrayAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.NbtLongArrayAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.UUID;

public class NetworkList implements NetworkSupport {

	private final MixedList elements = new MixedArrayList();

	private <T> NetworkPrimitive<T> nullable(NetworkPrimitive<T> primitive) {
		return ObjectUtils.ifNullMakeDefault(primitive, NetworkPrimitive::empty);
	}

	public <T extends NetworkSupport> T get(int index, Class<T> type) {
		return this.elements.get(index, type);
	}

	public byte getByte(int index) {
		return this.nullable(this.get(index, NetworkByte.class)).getValue();
	}

	public short getShort(int index) {
		return this.nullable(this.get(index, NetworkShort.class)).getValue();
	}

	public int getInt(int index) {
		return this.nullable(this.get(index, NetworkInteger.class)).getValue();
	}

	public long getLong(int index) {
		return this.nullable(this.get(index, NetworkLong.class)).getValue();
	}

	public Identifier getIdentifier(int index) {
		return this.nullable(this.get(index, NetworkIdentifier.class)).getValue();
	}

	public UUID getUuid(int index) {
		return this.nullable(this.get(index, NetworkUUID.class)).getValue();
	}

	public float getFloat(int index) {
		return this.nullable(this.get(index, NetworkFloat.class)).getValue();
	}

	public double getDouble(int index) {
		return this.nullable(this.get(index, NetworkDouble.class)).getValue();
	}

	public String getString(int index) {
		return this.nullable(this.get(index, NetworkString.class)).getValue();
	}

	public byte[] getByteArray(int index) {
		return this.nullable(this.get(index, NetworkByteArray.class)).getValue();
	}

	public int[] getIntArray(int index) {
		return this.nullable(this.get(index, NetworkIntegerArray.class)).getValue();
	}

	public long[] getLongArray(int index) {
		return this.nullable(this.get(index, NetworkLongArray.class)).getValue();
	}

	public boolean getBoolean(int index) {
		return this.nullable(this.get(index, NetworkBoolean.class)).getValue();
	}

	public ItemStack getItemStack(int index) {
		return this.nullable(this.get(index, NetworkItemStack.class)).getValue();
	}

	public <T extends NetworkSupport> void add(Class<T> type, T value) {
		this.elements.add(type, value);
	}

	public void addByte(byte value) {
		this.add(NetworkByte.class, NetworkByte.of(value));
	}

	public void addShort(short value) {
		this.add(NetworkShort.class, NetworkShort.of(value));
	}

	public void addInt(int value) {
		this.add(NetworkInteger.class, NetworkInteger.of(value));
	}

	public void addLong(long value) {
		this.add(NetworkLong.class, NetworkLong.of(value));
	}

	public void addIdentifier(Identifier value) {
		this.add(NetworkIdentifier.class, NetworkIdentifier.of(value));
	}

	public void addUuid(UUID value) {
		this.add(NetworkUUID.class, NetworkUUID.of(value));
	}

	public void addFloat(float value) {
		this.add(NetworkFloat.class, NetworkFloat.of(value));
	}

	public void addDouble(double value) {
		this.add(NetworkDouble.class, NetworkDouble.of(value));
	}

	public void addString(String value) {
		this.add(NetworkString.class, NetworkString.of(value));
	}

	public void addByteArray(byte[] value) {
		this.add(NetworkByteArray.class, NetworkByteArray.of(value));
	}

	public void addByteArray(List<Byte> value) {
		this.addByteArray(NbtByteArrayAccessor.invokeToArray(value));
	}

	public void addIntArray(int[] value) {
		this.add(NetworkIntegerArray.class, NetworkIntegerArray.of(value));
	}

	public void addIntArray(List<Integer> value) {
		this.addIntArray(NbtIntArrayAccessor.invokeToArray(value));
	}

	public void addLongArray(long[] value) {
		this.add(NetworkLongArray.class, NetworkLongArray.of(value));
	}

	public void addLongArray(List<Long> value) {
		this.addLongArray(NbtLongArrayAccessor.invokeToArray(value));
	}

	public void addBoolean(boolean value) {
		this.add(NetworkBoolean.class, NetworkBoolean.of(value));
	}

	public void addItemStack(ItemStack value) {
		this.add(NetworkItemStack.class, NetworkItemStack.of(value));
	}

	public <T extends NetworkSupport> void remove(Class<T> type, T value) {
		this.elements.remove(type, value);
	}

	public void removeByte(byte value) {
		this.remove(NetworkByte.class, NetworkByte.of(value));
	}

	public void removeShort(short value) {
		this.remove(NetworkShort.class, NetworkShort.of(value));
	}

	public void removeInt(int value) {
		this.remove(NetworkInteger.class, NetworkInteger.of(value));
	}

	public void removeLong(long value) {
		this.remove(NetworkLong.class, NetworkLong.of(value));
	}

	public void removeIdentifier(Identifier value) {
		this.remove(NetworkIdentifier.class, NetworkIdentifier.of(value));
	}

	public void removeUuid(UUID value) {
		this.remove(NetworkUUID.class, NetworkUUID.of(value));
	}

	public void removeFloat(float value) {
		this.remove(NetworkFloat.class, NetworkFloat.of(value));
	}

	public void removeDouble(double value) {
		this.remove(NetworkDouble.class, NetworkDouble.of(value));
	}

	public void removeString(String value) {
		this.remove(NetworkString.class, NetworkString.of(value));
	}

	public void removeByteArray(byte[] value) {
		this.remove(NetworkByteArray.class, NetworkByteArray.of(value));
	}

	public void removeByteArray(List<Byte> value) {
		this.removeByteArray(NbtByteArrayAccessor.invokeToArray(value));
	}

	public void removeIntArray(int[] value) {
		this.remove(NetworkIntegerArray.class, NetworkIntegerArray.of(value));
	}

	public void removeIntArray(List<Integer> value) {
		this.removeIntArray(NbtIntArrayAccessor.invokeToArray(value));
	}

	public void removeLongArray(long[] value) {
		this.remove(NetworkLongArray.class, NetworkLongArray.of(value));
	}

	public void removeLongArray(List<Long> value) {
		this.removeLongArray(NbtLongArrayAccessor.invokeToArray(value));
	}

	public void removeBoolean(boolean value) {
		this.remove(NetworkBoolean.class, NetworkBoolean.of(value));
	}

	public void removeItemStack(ItemStack value) {
		this.remove(NetworkItemStack.class, NetworkItemStack.of(value));
	}

	public <T extends NetworkSupport> void set(int index, Class<T> type, T value) {
		this.elements.set(index, type, value);
	}

	public void setByte(int index, byte value) {
		this.set(index, NetworkByte.class, NetworkByte.of(value));
	}

	public void setShort(int index, short value) {
		this.set(index, NetworkShort.class, NetworkShort.of(value));
	}

	public void setInt(int index, int value) {
		this.set(index, NetworkInteger.class, NetworkInteger.of(value));
	}

	public void setLong(int index, long value) {
		this.set(index, NetworkLong.class, NetworkLong.of(value));
	}

	public void setIdentifier(int index, Identifier value) {
		this.set(index, NetworkIdentifier.class, NetworkIdentifier.of(value));
	}

	public void setUuid(int index, UUID value) {
		this.set(index, NetworkUUID.class, NetworkUUID.of(value));
	}

	public void setFloat(int index, float value) {
		this.set(index, NetworkFloat.class, NetworkFloat.of(value));
	}

	public void setDouble(int index, double value) {
		this.set(index, NetworkDouble.class, NetworkDouble.of(value));
	}

	public void setString(int index, String value) {
		this.set(index, NetworkString.class, NetworkString.of(value));
	}

	public void setByteArray(int index, byte[] value) {
		this.set(index, NetworkByteArray.class, NetworkByteArray.of(value));
	}

	public void setByteArray(int index, List<Byte> value) {
		this.setByteArray(index, NbtByteArrayAccessor.invokeToArray(value));
	}

	public void setIntArray(int index, int[] value) {
		this.set(index, NetworkIntegerArray.class, NetworkIntegerArray.of(value));
	}

	public void setIntArray(int index, List<Integer> value) {
		this.setIntArray(index, NbtIntArrayAccessor.invokeToArray(value));
	}

	public void setLongArray(int index, long[] value) {
		this.set(index, NetworkLongArray.class, NetworkLongArray.of(value));
	}

	public void setLongArray(int index, List<Long> value) {
		this.setLongArray(index, NbtLongArrayAccessor.invokeToArray(value));
	}

	public void setBoolean(int index, boolean value) {
		this.set(index, NetworkBoolean.class, NetworkBoolean.of(value));
	}

	public void setItemStack(int index, ItemStack value) {
		this.set(index, NetworkItemStack.class, NetworkItemStack.of(value));
	}

	@ShouldNotUse(useInstead = "NetworkSupport#readComplete")
	public static NetworkList read(PacketByteBuf buf) {
		NetworkList list = new NetworkList();
		list.elements.addAll(buf.readList(current -> TypedObject.of(NetworkSupport.getType(current), NetworkSupport.readComplete(current))));
		return list;
	}

	@Override
	public void write(PacketByteBuf buf) {
		buf.writeCollection(this.elements, (current, typed) -> ((NetworkSupport) typed.getValue()).writeComplete(current));
	}

	public MixedList getElements() {
		return new MixedArrayList(this.elements);
	}

	static {
		NetworkSupport.register(new MModdingIdentifier("list"), NetworkList.class, NetworkList::read);
	}
}
