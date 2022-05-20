package event;

import java.util.EventListener;

/**
 *
 * @author davibern
 * @version 1.0
 */
public interface ModificarBDListener extends EventListener {   
    public void capturarModificacionBD(ModificarBDEvent ev);
}
