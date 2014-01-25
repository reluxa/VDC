package org.reluxa.settings.service;

import org.reluxa.settings.Config;

public interface SettingsServiceIF {
	
	Config getConfig();

	void saveConfig(Config config);
	
}
