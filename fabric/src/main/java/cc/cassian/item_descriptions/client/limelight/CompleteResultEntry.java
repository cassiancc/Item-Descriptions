package cc.cassian.item_descriptions.client.limelight;

import io.wispforest.limelight.api.entry.SetSearchTextEntry;

public class CompleteResultEntry extends BaseResultProvider implements SetSearchTextEntry {
        @Override
        public String newSearchText() {
            return "items";
        }
    }
