# Changelog

## [0.2.0] - 2025-10-01
### Features
- Bundle the CHW imagery and record button selector so downstream builds can
  surface the visited-state visuals without custom assets.

### Bug Fixes
- Point toolbar, snackbar, and dialog components at shared Smart Register and
  JsonWizard identifiers to avoid resource overrides when CHW overlays are
  installed.

### Maintenance
- Restore legacy CHW resource aliases and dialog styling to prevent missing
  resource crashes when the compatibility resources are requested.

## [0.1.0] - 2025-09-27
### Features
- Add the CHW-facing PNC compatibility layer (activities, contracts, presenters,
  interactors, repositories, and resources) so downstream CHW apps can depend on
  the published artifact without bundling the legacy snapshot.

### Build
- Resolve the CHW ANC dependency from the published GitHub artifact and wire in
  the auxiliary libraries that used to ship with the local AAR to keep builds
  self-contained.
- Publish artifacts to Maven Local under the repo-qualified group during
  JitPack builds so the CI pipeline mirrors the coordinates exposed by the new
  dependencies.

## [0.0.8] - 2025-09-26
### Bug Fixes
- Normalize the page counter string to use positional formatting so all
  consumers compile without aapt merge failures.
- Drop the redundant `actionBarSize` override that conflicted with
  AppCompat's attribute definition.

## [0.0.7] - 2025-09-26
### Bug Fixes
- Switch all formatted strings to positional placeholders to satisfy
  aapt's formatting rules.
- Override the duplicate `actionBarSize` attribute to prevent resource
  merge conflicts with the latest native-form artifacts.

## [0.0.6] - 2025-09-26
### Bug Fixes
- Replace the deprecated image compression workflow with sampled bitmap decoding and
  update the related tests to keep form image handling working across builds.
- Initialize SnakeYAML with explicit loader options so the library compiles with
  SnakeYAML 2.x on modern toolchains.

### Dependency Updates
- Align native form artifacts to the GitHub-hosted coordinates, including the
  circle progress component, to keep Maven publishing resolvable.
