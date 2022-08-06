package skylands.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skylands.Mod;
import skylands.logic.Skylands;

import java.util.UUID;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {

	public BlockItemMixin(Settings settings) {
		super(settings);
	}

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		World world = context.getWorld();
		PlayerEntity player = context.getPlayer();
		if(!world.isClient && player != null) {
			if(world.getRegistryKey().getValue().getNamespace().equals(Mod.MOD_ID)) {
				var island = Skylands.instance.islandStuck.get(UUID.fromString(world.getRegistryKey().getValue().getPath()));
				if(island.isPresent() && !island.get().isMember(player)) {
					player.sendMessage(Text.of("Skylands > You can't place blocks out here!"), true);
					cir.setReturnValue(ActionResult.FAIL);
				}
			}
		}
	}
}
