# Changelog

## [0.0.6] - 2025-09-26
### Bug Fixes
- Replace the deprecated image compression workflow with sampled bitmap decoding and
  update the related tests to keep form image handling working across builds.
- Initialize SnakeYAML with explicit loader options so the library compiles with
  SnakeYAML 2.x on modern toolchains.

### Dependency Updates
- Align native form artifacts to the GitHub-hosted coordinates, including the
  circle progress component, to keep Maven publishing resolvable.
