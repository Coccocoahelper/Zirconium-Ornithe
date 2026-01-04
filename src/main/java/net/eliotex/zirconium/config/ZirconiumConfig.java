/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * For more information, see the LICENSE file.
 */
package net.eliotex.zirconium.config;

import io.github.axolotlclient.AxolotlClientConfig.api.AxolotlClientConfig;
import io.github.axolotlclient.AxolotlClientConfig.api.manager.ConfigManager;
import io.github.axolotlclient.AxolotlClientConfig.api.options.OptionCategory;
import io.github.axolotlclient.AxolotlClientConfig.impl.managers.VersionedJsonConfigManager;
import io.github.axolotlclient.AxolotlClientConfig.impl.options.BooleanOption;
import net.eliotex.zirconium.Zirconium;
import lombok.Getter;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents;

import java.util.function.Supplier;

public class ZirconiumConfig {

	public static ZirconiumConfig instance = new ZirconiumConfig();

	@Getter
    private final OptionCategory category = OptionCategory.create(Zirconium.MODID);
	private final OptionCategory details = OptionCategory.create("details");

	public final BooleanOption sky = new BooleanOption("sky", true);
	public final BooleanOption clouds = new BooleanOption("clouds", true);
	public final BooleanOption stars = new BooleanOption("stars", true);
	public final BooleanOption sun = new BooleanOption("sun", true);
	public final BooleanOption moon = new BooleanOption("moon", true);
	public final BooleanOption fog = new BooleanOption("fog", true);
	public final BooleanOption disableTextShadows = new BooleanOption("disableTextShadows", false);
	public final BooleanOption hideDownloadingTerrainScreen = new BooleanOption("hideDownloadingTerrainScreen", true);

	public void initConfig() {
		category.add(
			details
		);
		category.add(
			disableTextShadows,
			hideDownloadingTerrainScreen
		);

		details.add(
			sky,
			clouds,
			stars,
			sun,
			moon,
			fog
		);

		ConfigManager configManager = new VersionedJsonConfigManager(FabricLoader.getInstance().getConfigDir().resolve(Zirconium.MODID + ".json"),
			category, 1, (configVersion, configVersion1, optionCategory, jsonObject) -> jsonObject);
		AxolotlClientConfig.getInstance().register(configManager);
		configManager.load();
	}
}