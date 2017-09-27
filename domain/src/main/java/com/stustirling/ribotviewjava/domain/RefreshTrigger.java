package com.stustirling.ribotviewjava.domain;

import android.support.annotation.Nullable;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public class RefreshTrigger {

    public interface Listener {
        void refreshTriggered();
    }

    private Listener listener;

    public void setListener(@Nullable Listener listener) {
        this.listener = listener;
    }

    private boolean enabled = true;

    public void refresh() {
        if ( listener!=null && isEnabled()) {
            listener.refreshTriggered();
        }
    }

    private boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
