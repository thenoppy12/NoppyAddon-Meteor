//package sources.net.thenoppy12.noppyaddon.mixin;
//
//import meteordevelopment.meteorclient.systems.modules.Modules;
//import net.minecraft.client.ClientBrandRetriever;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//import sources.net.thenoppy12.noppyaddon.modules.SerberSpoofModule;
//
//@Mixin(ClientBrandRetriever.class)
//public class ClientBrandReceiverMixin {
//    @Inject(at = @At("HEAD"), method = "getClientModName", cancellable = true, remap = false)
//    private static void getClientModName(CallbackInfoReturnable<String> ci) {
//        if (Modules.get().isActive(SerberSpoofModule.class)) {
//            ci.setReturnValue("vanilla");
//        }
//        else {
//            ci.setReturnValue("fabric");
//        }
//    }
//}
