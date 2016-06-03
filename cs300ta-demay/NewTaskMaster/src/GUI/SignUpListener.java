package GUI;

import java.util.EventListener;

/**
 * Avery DeMay
 * CS 300
 * Task Master
 *
 * Sign up Event Listener
 */
public interface SignUpListener extends EventListener{
    public void formEventOccurred(UserFormEvent e);
}
