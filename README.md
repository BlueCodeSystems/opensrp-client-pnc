<!-- JITPACK BADGES:START -->
[![JitPack Latest](https://jitpack.io/v/BlueCodeSystems/opensrp-client-pnc.svg)](https://jitpack.io/#BlueCodeSystems/opensrp-client-pnc)
[![master-SNAPSHOT](https://jitpack.io/v/BlueCodeSystems/opensrp-client-pnc/master-SNAPSHOT.svg)](https://jitpack.io/#BlueCodeSystems/opensrp-client-pnc/master-SNAPSHOT)
<!-- JITPACK BADGES:END -->

# opensrp-client-pnc
Android client components for OpenSRP postnatal care (PNC) workflows, including configurable registration, visit management, and repository utilities for facility deployments.

## Project Status
- Toolchain: Gradle 8.7 · Android Gradle Plugin 8.6.0 · Kotlin 1.9.22 · JDK 17
- CI: no active pipeline in this repo; run Gradle checks locally before opening PRs
- Branch: `master` (default working branch)
- Tags: no tagged releases published yet; track milestones via Git history

## Features
- PNC registration, profile, and visit flows backed by reusable `BasePnc*` activities and presenters
- YAML-driven configuration for registers, form processing, and visit scheduling
- Repositories for mothers, babies, partial forms, and visit summaries with offline storage helpers
- Integration hooks for OpenSRP client/core services (unique IDs, sync, rules engine, job scheduling)

## Requirements
- JDK 17
- Gradle 8.7 with Android Gradle Plugin 8.6.0
- Kotlin 1.9.22 (bundled with the toolchain)
- Android minSdk 28
- Android compileSdk / targetSdk 35

## Install
Most deployments consume artifacts published to Maven Central.

<details>
<summary>Groovy</summary>

```groovy
repositories {
  mavenCentral()
}

dependencies {
  implementation 'org.smartregister:opensrp-client-pnc:<version>' // see Releases for the current version
}
```
</details>

<details>
<summary>Kotlin DSL</summary>

```kotlin
dependencyResolutionManagement {
    repositories { mavenCentral() }
}

dependencies {
    implementation("org.smartregister:opensrp-client-pnc:<version>") // see Releases for the current version
}
```
</details>

## Initialize
Add library initialization to your application once OpenSRP core has been bootstrapped. The sample app demonstrates a full setup.

```java
public class MyPncApplication extends DrishtiApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = Context.getInstance();
        context.updateApplicationContext(getApplicationContext());

        PncMetadata metadata = new PncMetadata(
                PncConstants.Form.PNC_REGISTRATION,
                PncDbConstants.KEY.TABLE,
                PncConstants.EventTypeConstants.PNC_REGISTRATION,
                PncConstants.EventTypeConstants.UPDATE_PNC_REGISTRATION,
                PncConstants.CONFIG,
                MyPncFormActivity.class,
                BasePncProfileActivity.class,
                true
        );

        PncConfiguration configuration = new PncConfiguration.Builder(MyRegisterQueryProvider.class)
                .setPncMetadata(metadata)
                .build();

        PncLibrary.init(context, getRepository(), configuration, BuildConfig.VERSION_CODE, BuildConfig.DATABASE_VERSION);
    }
}
```

## Usage examples
Fetch PNC children linked to a mother and work with the domain model:

```java
List<PncChild> babies = PncLibrary.getInstance()
        .getPncChildRepository()
        .findAllByMotherBaseEntityId(motherBaseEntityId);
```

Start a JSON Wizard form using your concrete form activity (see `sample-app` for an example subclass):

```java
Intent intent = new Intent(activity, PncFormActivity.class);
intent.putExtra(PncConstants.IntentKey.BASE_ENTITY_ID, baseEntityId);
intent.putExtra(PncConstants.JsonFormExtraConstants.JSON, formJsonString);
activity.startActivity(intent);
```

Calculate visit status with the configurable scheduler:

```java
// import org.joda.time.LocalDate;
PncVisitScheduler scheduler = PncLibrary.getInstance().getPncVisitScheduler();
scheduler.setDeliveryDate(new LocalDate(deliveryDate));
scheduler.setLatestVisitDateInMills(String.valueOf(lastVisitTimestamp));
VisitStatus status = scheduler.getStatus();
```

## Sample app
A runnable reference implementation lives under `sample-app/`.

- Install on a device or emulator: `./gradlew :sample-app:installDebug`
- Alternatively open the project in Android Studio and run the **pnc-sample** configuration.

## Build & test
- Assemble library artifacts: `./gradlew clean assemble`
- Run unit tests: `./gradlew test`

## Releases
Version notes and change history are published under [Releases](https://github.com/BlueCodeSystems/opensrp-client-pnc/releases). Use the latest tagged version in your build.

## Contributing
Contributions are welcome! Please open an issue or pull request, keep changes compatible with the stated toolchain, and run the Gradle checks above before submitting.

## License
This project is licensed under the [Apache License 2.0](LICENSE).
