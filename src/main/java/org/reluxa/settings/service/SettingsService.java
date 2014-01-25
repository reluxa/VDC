package org.reluxa.settings.service;

import org.reluxa.AbstractService;
import org.reluxa.Log;
import org.reluxa.settings.Config;

import com.db4o.ObjectSet;

public class SettingsService extends AbstractService implements SettingsServiceIF {
	
	public static Config DEFAULT = createDefault(); 
	
	private static Config createDefault() {
		Config config = new Config();
		config.setNumberOfEventsPerWeek(5);
		return config;
	}
	
	@Override
  public Config getConfig() {
		ObjectSet<Config> os = db.query(Config.class);
		if (os.size() == 0) {
			return DEFAULT;
		} else {
			return os.get(0);
		}
  }

	@Override
  public void saveConfig(Config config) {
		db.store(config);
  }

}
