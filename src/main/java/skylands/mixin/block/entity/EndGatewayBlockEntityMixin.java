package skylands.mixin.block.entity;

import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import skylands.util.Worlds;

@Mixin(EndGatewayBlockEntity.class)
public class EndGatewayBlockEntityMixin {

	@Redirect(method = "tryTeleportingEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getRegistryKey()Lnet/minecraft/util/registry/RegistryKey;"))
	private static RegistryKey<World> tryTeleportingEntity_redirectRegistryKey(World instance) {
		return Worlds.redirect(instance.getRegistryKey());
	}

}
