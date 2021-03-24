package logisticspipes.pipes;

import logisticspipes.textures.Textures;
import logisticspipes.textures.Textures.TextureType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class PipeLogisticsChassiMk1 extends PipeLogisticsChassi {

	public PipeLogisticsChassiMk1(Item item) {
		super(item);
	}

	@Override
	public TextureType getCenterTexture() {
		return Textures.LOGISTICSPIPE_CHASSI1_TEXTURE;
	}

	@Override
	public int getChassiSize() {
		return 2;
	}

	private static final ResourceLocation TEXTURE = new ResourceLocation("logisticspipes", "textures/gui/mk1.png");

	@Override
	public ResourceLocation getChassiGUITexture() {
		return PipeLogisticsChassiMk1.TEXTURE;
	}

}
