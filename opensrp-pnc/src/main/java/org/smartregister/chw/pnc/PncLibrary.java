package org.smartregister.chw.pnc;

import androidx.annotation.NonNull;

import org.smartregister.Context;
import org.smartregister.chw.pnc.repository.PncCloseDateRepository;
import org.smartregister.chw.pnc.repository.ProfileRepository;
import org.smartregister.repository.Repository;

/**
 * Thin singleton wrapper preserved for modules that still bootstrap the CHW PNC library directly.
 */
public final class PncLibrary {

    private static PncLibrary instance;

    private final Context context;
    private final Repository repository;
    private final int applicationVersion;
    private final int databaseVersion;

    private ProfileRepository profileRepository;
    private PncCloseDateRepository pncCloseDateRepository;

    private PncLibrary(@NonNull Context context,
                       @NonNull Repository repository,
                       int applicationVersion,
                       int databaseVersion) {
        this.context = context;
        this.repository = repository;
        this.applicationVersion = applicationVersion;
        this.databaseVersion = databaseVersion;
    }

    public static synchronized void init(@NonNull Context context,
                                         @NonNull Repository repository,
                                         int applicationVersion,
                                         int databaseVersion) {
        if (instance == null) {
            instance = new PncLibrary(context, repository, applicationVersion, databaseVersion);
        }
    }

    @NonNull
    public static PncLibrary getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                    "Instance does not exist. Call " + PncLibrary.class.getName() +
                            ".init(...) in the Application onCreate().");
        }
        return instance;
    }

    public Context context() {
        return context;
    }

    public Repository getRepository() {
        return repository;
    }

    public int getApplicationVersion() {
        return applicationVersion;
    }

    public int getDatabaseVersion() {
        return databaseVersion;
    }

    public synchronized ProfileRepository profileRepository() {
        if (profileRepository == null) {
            profileRepository = new ProfileRepository();
        }
        return profileRepository;
    }

    public synchronized PncCloseDateRepository getPncCloseDateRepository() {
        if (pncCloseDateRepository == null) {
            pncCloseDateRepository = new PncCloseDateRepository();
        }
        return pncCloseDateRepository;
    }
}
