package trackup;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.application.Application;
import trackup.commons.core.LogsCenter;
import trackup.commons.util.FileUtil;
import trackup.commons.util.ToStringBuilder;

/**
 * Represents the parsed command-line parameters given to the application.
 */
public class AppParameters {
    private static final Logger logger = LogsCenter.getLogger(AppParameters.class);

    private Path configPath;

    public Path getConfigPath() {
        return configPath;
    }

    public void setConfigPath(Path configPath) {
        this.configPath = configPath;
    }

    /**
     * Parses the application command-line parameters.
     */
    public static AppParameters parse(Application.Parameters parameters) {
        AppParameters appParameters = new AppParameters();
        Map<String, String> namedParameters = parameters.getNamed();

        String configPathParameter = namedParameters.get("config");
        if (configPathParameter != null && !FileUtil.isValidPath(configPathParameter)) {
            logger.warning("Invalid config path " + configPathParameter + ". Using default config path.");
            configPathParameter = null;
        }
        appParameters.setConfigPath(configPathParameter != null ? Paths.get(configPathParameter) : null);

        return appParameters;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppParameters)) {
            return false;
        }

        AppParameters otherAppParameters = (AppParameters) other;
        return Objects.equals(configPath, otherAppParameters.configPath);
    }

    @Override
    public int hashCode() {
        return configPath.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("configPath", configPath)
                .toString();
    }
}
