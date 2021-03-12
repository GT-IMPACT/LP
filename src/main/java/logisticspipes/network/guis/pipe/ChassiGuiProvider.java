package logisticspipes.network.guis.pipe;

import logisticspipes.LogisticsPipes;
import logisticspipes.gui.GuiChassiPipe;
import logisticspipes.interfaces.ISlotCheck;
import logisticspipes.modules.abstractmodules.LogisticsModule;
import logisticspipes.network.abstractguis.BooleanModuleCoordinatesGuiProvider;
import logisticspipes.network.abstractguis.GuiProvider;
import logisticspipes.pipes.PipeLogisticsChassi;
import logisticspipes.pipes.basic.LogisticsTileGenericPipe;
import logisticspipes.pipes.upgrades.ModuleUpgradeManager;
import logisticspipes.utils.gui.DummyContainer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ChassiGuiProvider extends BooleanModuleCoordinatesGuiProvider {

    public ChassiGuiProvider(int id) {
        super(id);
    }

    @Override
    public Object getClientGui(EntityPlayer player) {
        LogisticsTileGenericPipe pipe = getPipe(player.getEntityWorld());
        if (pipe == null || !(pipe.pipe instanceof PipeLogisticsChassi)) {
            return null;
        }
        return new GuiChassiPipe(player, (PipeLogisticsChassi) pipe.pipe, isFlag());
    }

    @Override
    public DummyContainer getContainer(EntityPlayer player) {
        LogisticsTileGenericPipe pipe = getPipe(player.getEntityWorld());
        if (pipe == null || !(pipe.pipe instanceof PipeLogisticsChassi)) {
            return null;
        }
        final PipeLogisticsChassi _chassiPipe = (PipeLogisticsChassi) pipe.pipe;
        IInventory _moduleInventory = _chassiPipe.getModuleInventory();
        DummyContainer dummy = new DummyContainer(player.inventory, _moduleInventory);
        if (_chassiPipe.getChassiSize() < 5) {
            dummy.addNormalSlotsForPlayerInventory(18, 97);
        } else {
            dummy.addNormalSlotsForPlayerInventory(18, 174);
        }

        getSlotsModules(dummy, _chassiPipe, _moduleInventory);

        if (_chassiPipe.getUpgradeManager().hasUpgradeModuleUpgrade()) {
            for (int i = 0; i < _chassiPipe.getChassiSize(); i++) {
                final int fI = i;
                ModuleUpgradeManager upgradeManager = _chassiPipe.getModuleUpgradeManager(i);
                dummy.addRestrictedSlot(0, upgradeManager.getInv(), 145, 9 + i * 20, new ISlotCheck() {

                    @Override
                    public boolean isStackAllowed(ItemStack itemStack) {
                        return ChassiGuiProvider.checkStack(itemStack, _chassiPipe, fI);
                    }
                });
                dummy.addRestrictedSlot(1, upgradeManager.getInv(), 165, 9 + i * 20, new ISlotCheck() {

                    @Override
                    public boolean isStackAllowed(ItemStack itemStack) {
                        return ChassiGuiProvider.checkStack(itemStack, _chassiPipe, fI);
                    }
                });
            }
        }
        return dummy;
    }

    private void getSlotsModules(DummyContainer dummy, PipeLogisticsChassi _chassiPipe, IInventory _moduleInventory) {
        int xCoordModule = 18;
        int rowY = 0;
        int rowX = 0;
        for (int i = 0; i < _chassiPipe.getChassiSize(); i++) {
            if (i % 8 == 0) {
                rowY = 0;
                rowX++;
            }
            dummy.addModuleSlot(i, _moduleInventory, xCoordModule + (rowX * 36 - 18), 9 + 20 * rowY++, _chassiPipe);
        }
    }

    public static boolean checkStack(ItemStack stack, PipeLogisticsChassi chassiPipe, int moduleSlot) {
        if (stack == null) {
            return false;
        }
        if (!stack.getItem().equals(LogisticsPipes.UpgradeItem)) {
            return false;
        }
        LogisticsModule module = chassiPipe.getModules().getModule(moduleSlot);
        if (module == null) {
            return false;
        }
        if (!LogisticsPipes.UpgradeItem.getUpgradeForItem(stack, null).isAllowedForModule(module)) {
            return false;
        }
        return true;
    }

    @Override
    public GuiProvider template() {
        return new ChassiGuiProvider(getId());
    }
}