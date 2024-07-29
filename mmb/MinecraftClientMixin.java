//package sources.net.thenoppy12.noppyaddon.mixin;
//
//import meteordevelopment.meteorclient.systems.modules.Modules;
//import net.minecraft.client.MinecraftClient;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//import sources.net.thenoppy12.noppyaddon.modules.SerberSpoofModule;
//@Mixin(MinecraftClient.class)
//public abstract class MinecraftClientMixin {
//    @Inject(method="getVersionType", at =@At("HEAD"), cancellable = true)
//    public void getVersionType(CallbackInfoReturnable<String> ci){
//        if(Modules.get().isActive(SerberSpoofModule.class)) {
//            ci.setReturnValue("release"); // sussy release lmfao :))))
//        }
//    }
//}
