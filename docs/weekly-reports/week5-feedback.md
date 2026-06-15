# Week 5 Project Feedback by PM/TA

**Dedicated PM/TA**: Vandan Agrawal (vandanagrawal@u.northwestern.edu)

## How to Read This Feedback
> [!NOTE]
> **Purpose.** This feedback focuses on your team's progress and collaboration. It is meant as guidance, not judgement.

> [!IMPORTANT]
> **Scope.** For the BVA and TDD items, the PM/TA evaluates only the `main` branch. Ongoing work in feature branches will be evaluated after it is merged. If you'd like early feedback on work in progress, please reach out to your PM/TA directly.

> [!TIP]
> **Mistakes are expected :).** As the instructor mentioned in class, early mistakes are part of the learning process. As long as your team addresses the issues after you get the feedback, your grade will not suffer from them.

## Checklist
Status:
- ✅: All done/Good job!
- ⚠️: Attention needed
- ❌: Significant issue found
- ➖: No basis to evaluate

### Past Feedback
| # | Item                                                                                                 | Status | Reviewer Notes | Source Instructions or Resources |
|---|------------------------------------------------------------------------------------------------------|:------:|----------------|----------------------------------|
| 0 | The team has closed and merged the past Feedback PR(s), indicating that they have read the feedback. | ❌ | Found a `week 4 feedback` PR still open (not merged). Please resolve reviewer comments and merge it to demonstrate feedback closure. | Week 4/5 feedback process |

### Software Process Quality
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1 | Each active feature branch has an open draft PR against main.                                                                                                | ⚠️ | Open PR activity exists, but observed open PRs are not draft and branch-to-task workflow is unclear. Convert in-progress work to draft PRs with clear scope and keep them updated weekly. | Week 4 Wednesday Lecture (Lecture 08) |
| 2 | The team has a “definition of done” (BVA) fully documented for the part of the system that is done. (needed for Letter Grade D)                              | ❌ | `docs/bva` only contains the template README; no class-level BVA files or implemented test-case traceability found. | Project grading rubrics |
| 3 | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code. (needed for Letter Grade C)                                       | ❌ | Commit history mostly shows setup/report edits; no domain implementation plus corresponding test-first/test-driven commit sequence found in `main`. | Project grading rubrics |

### Planning & Progress Evaluation
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 4 | The team documents every week’s planning and progress evaluation professionally. (needed for Letter Grade B)                                                 | ⚠️ | `docs/weekly-reports/report.md` has one partially filled Week 3 section and a placeholder template; Week 4/5 planning and reflection are not documented to expected detail. | Week 4 Wednesday Lecture (Lecture 08), Project grading rubrics |

### Progress & Collaboration
| # | Item                                                                                                                                                                                   |  Status   | Reviewer Notes      | Source Instructions or Resources                 |
|---|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|--------------------------------------------------|
| 5 | Overall development progress (recall the recommended order is: Game Setup Phase -> One turn of the game -> Multiple turns -> One win condition -> Other win conditions (if applicable) | ❌ | Current `main` branch appears to be at scaffolding stage (project skeleton, CI, placeholders). No playable turn loop/win-condition implementation is visible yet. | Canvas assignment Project: Week 4 and 5 Guidance |
| 6 | Collaboration: Quality of discussion in PR reviews and work item comments on the board.                                                                                                | ❌ | No clear or proper description for the PRs and no proper usable comments on the board as well | |

### The following items are not checked by the reviewer as they were checked in the previous weeks
But if your team wants the reviewer to check any of these for any reasons, please contact them or the instructor via either email or tagging them in the feedback PR.

| #   | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|-----|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1   | GitHub repository branch protection rules are fully set up so that people cannot push into main without a pull request approval. (needed for Letter Grade C) | ➖ | Not checked this week since it was checked in Week 4. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 2   | Continuous Integration (CI) is fully set up from the beginning. (needed for Letter Grade B)                                                                  | ➖ | Not checked this week since it was checked in Week 4. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 3   | The team uses the project management board steadily and frequently, and the description of each task is detailed. (needed for Letter Grade B)                | ➖ | Not checked this week since it was checked in Week 4. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.1 | Every functionality-related work item on the management board includes a user story, and optionally one or more use cases.                                   | ➖ | Not checked this week since it was checked in Week 4. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.2 | The design is documented somewhere, either in the work item description, or in a separate design document.                                                   | ➖ | Not checked this week since it was checked in Week 4. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.3 | Task assignments are documented clearly in the management board.                                                                                             | ➖ | Not checked this week since it was checked in Week 4. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |

## Additional Comments
- Good start on repository setup and CI baseline.
- Highest-priority next steps for Week 5 expectations:
  1. Decide and document concrete game scope for this milestone in `docs/requirements/game-rules.md`.
  2. Implement one vertical slice on `main`: game setup + one valid turn + one test-backed rule.
  3. Add BVA files per implemented public method in `docs/bva/`.
  4. Update `docs/weekly-reports/report.md` with week-by-week plan, owner, PR links, and retrospective notes.
  5. Close/merge outstanding feedback PRs and keep active work in draft PRs with clear descriptions.
