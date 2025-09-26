package org.smartregister.chw.pnc.util;

/**
 * Namespaces constants previously exported by the legacy CHW PNC module.
 */
public final class Constants {

    private Constants() {
        // utility class
    }

    public static final class KEY {
        public static final String GENDER = "gender";

        private KEY() {
            // no-op
        }
    }

    public interface TABLES {
        String EC_CHILD = "ec_child";
        String EC_PREGNANCY_OUTCOME = "ec_pregnancy_outcome";
    }
}
