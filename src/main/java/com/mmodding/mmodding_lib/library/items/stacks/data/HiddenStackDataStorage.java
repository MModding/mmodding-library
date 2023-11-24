package com.mmodding.mmodding_lib.library.items.stacks.data;

import com.mmodding.mmodding_lib.ducks.ItemStackDuckInterface;
import com.mmodding.mmodding_lib.library.networking.NetworkSupport;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.library.utils.MixedHashMap;
import com.mmodding.mmodding_lib.library.utils.MixedMap;
import com.mmodding.mmodding_lib.library.utils.TypedObject;
import com.mmodding.mmodding_lib.mixin.accessors.NbtByteArrayAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.NbtIntArrayAccessor;
import com.mmodding.mmodding_lib.mixin.accessors.NbtLongArrayAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.UUID;

public class HiddenStackDataStorage implements NetworkSupport {

	public static HiddenStackDataStorage of(ItemStack stack) {
		return ((ItemStackDuckInterface) (Object) stack).mmodding_lib$getHiddenStackData();
	}

	protected final MixedMap<Identifier> entries = new MixedHashMap<>();

	public <T extends NetworkSupport> T get(Identifier identifier, Class<T> type) {
		return this.entries.get(identifier, type);
	}

	public byte getByte(Identifier key) {
		return this.entries.get(key, Byte.class);
	}

	public short getShort(Identifier key) {
		return this.entries.get(key, Short.class);
	}

	public int getInt(Identifier key) {
		return this.entries.get(key, Integer.class);
	}

	public long getLong(Identifier key) {
		return this.entries.get(key, Long.class);
	}

	public UUID getUuid(Identifier key) {
		return this.entries.get(key, UUID.class);
	}

	public float getFloat(Identifier key) {
		return this.entries.get(key, Float.class);
	}

	public double getDouble(Identifier key) {
		return this.entries.get(key, Double.class);
	}

	public String getString(Identifier key) {
		return this.entries.get(key, String.class);
	}

	public byte[] getByteArray(Identifier key) {
		return this.entries.get(key, byte[].class);
	}

	public int[] getIntArray(Identifier key) {
		return this.entries.get(key, int[].class);
	}

	public long[] getLongArray(Identifier key) {
		return this.entries.get(key, long[].class);
	}

	public boolean getBoolean(Identifier key) {
		return this.entries.get(key, Boolean.class);
	}

	public <T extends NetworkSupport> void put(Identifier identifier, Class<T> type, T value) {
		this.entries.put(identifier, type, value);
	}

	public void putByte(Identifier key, byte value) {
		this.entries.put(key, Byte.class, value);
	}

	public void putShort(Identifier key, short value) {
		this.entries.put(key, Short.class, value);
	}

	public void putInt(Identifier key, int value) {
		this.entries.put(key, Integer.class, value);
	}

	public void putLong(Identifier key, long value) {
		this.entries.put(key, Long.class, value);
	}

	public void putUuid(Identifier key, UUID value) {
		this.entries.put(key, UUID.class, value);
	}

	public void putFloat(Identifier key, float value) {
		this.entries.put(key, Float.class, value);
	}

	public void putDouble(Identifier key, double value) {
		this.entries.put(key, Double.class, value);
	}

	public void putString(Identifier key, String value) {
		this.entries.put(key, String.class, value);
	}

	public void putByteArray(Identifier key, byte[] value) {
		this.entries.put(key, byte[].class, value);
	}

	public void putByteArray(Identifier key, List<Byte> value) {
		this.putByteArray(key, NbtByteArrayAccessor.invokeToArray(value));
	}

	public void putIntArray(Identifier key, int[] value) {
		this.entries.put(key, int[].class, value);
	}

	public void putIntArray(Identifier key, List<Integer> value) {
		this.putIntArray(key, NbtIntArrayAccessor.invokeToArray(value));
	}

	public void putLongArray(Identifier key, long[] value) {
		this.entries.put(key, long[].class, value);
	}

	public void putLongArray(Identifier key, List<Long> value) {
		this.putLongArray(key, NbtLongArrayAccessor.invokeToArray(value));
	}

	public void putBoolean(Identifier key, boolean value) {
		this.entries.put(key, Boolean.class, value);
	}

	public MixedMap<Identifier> getEntries() {
		return new MixedHashMap<>(this.entries);
	}

	public static TypedObject<?> readTyped(PacketByteBuf buf) {
		PacketByteBuf check = new PacketByteBuf(buf.copy());
		Identifier identifier = check.readIdentifier();
		if (identifier.equals(Type.BYTE)) {
			buf.readIdentifier();
			return TypedObject.of(byte.class, buf.readByte());
		}
		else if (identifier.equals(Type.SHORT)) {
			buf.readIdentifier();
			return TypedObject.of(short.class, buf.readShort());
		}
		else if (identifier.equals(Type.INTEGER)) {
			buf.readIdentifier();
			return TypedObject.of(int.class, buf.readInt());
		}
		else if (identifier.equals(Type.LONG)) {
			buf.readIdentifier();
			return TypedObject.of(long.class, buf.readLong());
		}
		else if (identifier.equals(Type.UUID)) {
			buf.readIdentifier();
			return TypedObject.of(UUID.class, buf.readUuid());
		}
		else if (identifier.equals(Type.FLOAT)) {
			buf.readIdentifier();
			return TypedObject.of(float.class, buf.readFloat());
		}
		else if (identifier.equals(Type.DOUBLE)) {
			buf.readIdentifier();
			return TypedObject.of(double.class, buf.readDouble());
		}
		else if (identifier.equals(Type.STRING)) {
			buf.readIdentifier();
			return TypedObject.of(String.class, buf.readString());
		}
		else if (identifier.equals(Type.BYTE_ARRAY)) {
			buf.readIdentifier();
			return TypedObject.of(byte[].class, buf.readByteArray());
		}
		else if (identifier.equals(Type.INTEGER_ARRAY)) {
			buf.readIdentifier();
			return TypedObject.of(int[].class, buf.readIntArray());
		}
		else if (identifier.equals(Type.LONG_ARRAY)) {
			buf.readIdentifier();
			return TypedObject.of(long[].class, buf.readLongArray());
		}
		else if (identifier.equals(Type.BOOLEAN)) {
			buf.readIdentifier();
			return TypedObject.of(boolean.class, buf.readBoolean());
		}
		else if (identifier.equals(Type.CUSTOM)) {
			buf.readIdentifier();
			return TypedObject.of(NetworkSupport.class, NetworkSupport.readComplete(buf));
		}
		else {
			throw new IllegalArgumentException("Value is not readable");
		}
	}

	public <T> void writeTyped(PacketByteBuf buf, TypedObject<T> typed) {
		if (typed.getType() == byte.class) {
			buf.writeIdentifier(Type.BYTE);
			buf.writeByte((byte) typed.getValue());
		}
		else if (typed.getType() == short.class) {
			buf.writeIdentifier(Type.SHORT);
			buf.writeShort((short) typed.getValue());
		}
		else if (typed.getType() == int.class) {
			buf.writeIdentifier(Type.INTEGER);
			buf.writeVarInt((int) typed.getValue());
		}
		else if (typed.getType() == long.class) {
			buf.writeIdentifier(Type.LONG);
			buf.writeVarLong((long) typed.getValue());
		}
		else if (typed.getType() == UUID.class) {
			buf.writeIdentifier(Type.UUID);
			buf.writeUuid((UUID) typed.getValue());
		}
		else if (typed.getType() == float.class) {
			buf.writeIdentifier(Type.FLOAT);
			buf.writeFloat((float) typed.getValue());
		}
		else if (typed.getType() == double.class) {
			buf.writeIdentifier(Type.DOUBLE);
			buf.writeDouble((double) typed.getValue());
		}
		else if (typed.getType() == String.class) {
			buf.writeIdentifier(Type.STRING);
			buf.writeString((String) typed.getValue());
		}
		else if (typed.getType() == byte[].class) {
			buf.writeIdentifier(Type.BYTE_ARRAY);
			buf.writeByteArray((byte[]) typed.getValue());
		}
		else if (typed.getType() == int[].class) {
			buf.writeIdentifier(Type.INTEGER_ARRAY);
			buf.writeIntArray((int[]) typed.getValue());
		}
		else if (typed.getType() == long[].class) {
			buf.writeIdentifier(Type.LONG_ARRAY);
			buf.writeLongArray((long[]) typed.getValue());
		}
		else if (typed.getType() == boolean.class) {
			buf.writeIdentifier(Type.BOOLEAN);
			buf.writeBoolean((boolean) typed.getValue());
		}
		else if (NetworkSupport.REGISTRY.containsValue(typed.getType())) {
			buf.writeIdentifier(Type.CUSTOM);
			((NetworkSupport) typed.getValue()).writeComplete(buf);
		}
		else {
			throw new IllegalArgumentException("Value is not writable");
		}
	}

	public static HiddenStackDataStorage read(PacketByteBuf buf) {
		HiddenStackDataStorage storage = new HiddenStackDataStorage();
		storage.entries.putAll(buf.readMap(PacketByteBuf::readIdentifier, HiddenStackDataStorage::readTyped));
		return storage;
	}

	@Override
	public void write(PacketByteBuf buf) {
		buf.writeMap(this.entries, PacketByteBuf::writeIdentifier, this::writeTyped);
	}

	static {
		NetworkSupport.register(new MModdingIdentifier("hidden_stack_data_storage"), HiddenStackDataStorage.class, HiddenStackDataStorage::read);
	}

	public static class Type {

		public static final Identifier BYTE = new Identifier("java", "byte");
		public static final Identifier SHORT = new Identifier("java", "short");
		public static final Identifier INTEGER = new Identifier("java", "integer");
		public static final Identifier LONG = new Identifier("java", "long");
		public static final Identifier UUID = new Identifier("minecraft", "uuid");
		public static final Identifier FLOAT = new Identifier("java", "float");
		public static final Identifier DOUBLE = new Identifier("java", "double");
		public static final Identifier STRING = new Identifier("java", "string");
		public static final Identifier BYTE_ARRAY = new Identifier("java", "byte_array");
		public static final Identifier INTEGER_ARRAY = new Identifier("java", "integer_array");
		public static final Identifier LONG_ARRAY = new Identifier("java", "long_array");
		public static final Identifier BOOLEAN = new Identifier("java", "boolean");
		public static final Identifier CUSTOM = new MModdingIdentifier("custom");
	}
}
