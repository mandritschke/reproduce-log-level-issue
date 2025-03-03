package reproducer.ok;

import org.jboss.logging.Logger;
import org.keycloak.Config.Scope;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.UserStorageProviderFactory;

public class LogLevelReproducer implements UserStorageProviderFactory<LogLevelReproducer>, UserStorageProvider {

    private static final Logger LOG = Logger.getLogger(LogLevelReproducer.class);

    @Override
    public LogLevelReproducer create(KeycloakSession session, ComponentModel model) {
        return this;
    }

    @Override
    public String getId() {
        return "log-level-reproducer-with-simple-package-name";
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public void init(Scope config) {
        LOG.debug("Expected debug message");
        LOG.info("Expected info message");
        LOG.error("Expected error message");
    }

    @Override
    public void close() {
        // no-op
    }
}
