package skylands.logic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import skylands.api.SkylandsAPI;
import skylands.util.TeleportUtil;

public class Hub {
	public Vec3d pos = Skylands.config.defaultHubPos.toVec();
	public boolean hasProtection = Skylands.config.hubProtectedByDefault;

	public Hub() {
	}

	public void readFromNbt(NbtCompound nbt) {
		NbtCompound hubNbt = nbt.getCompound("hub");
		this.pos = new Vec3d(hubNbt.getDouble("x"), hubNbt.getDouble("y"), hubNbt.getDouble("z"));
		this.hasProtection = hubNbt.getBoolean("hasProtection");
	}

	public void writeToNbt(NbtCompound nbt) {
		NbtCompound hubNbt = new NbtCompound();
		hubNbt.putDouble("x", this.pos.x);
		hubNbt.putDouble("y", this.pos.y);
		hubNbt.putDouble("z", this.pos.z);
		hubNbt.putBoolean("hasProtection", this.hasProtection);
		nbt.put("hub", hubNbt);
	}

	public void visit(PlayerEntity player) {
		var world = Skylands.getServer().getOverworld();
		TeleportUtil.teleport((ServerPlayerEntity)player, world, pos.getX(), pos.getY(), pos.getZ(), Skylands.config.defaultHubPos.yaw, Skylands.config.defaultHubPos.pitch);
		SkylandsAPI.ON_HUB_VISIT.invoker().invoke(player, world);
	}
}
