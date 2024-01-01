# Contributing to Paper

Thanks for taking the time to help work on this. This is the guidelines to contribute. If this document needs to be changed, make a pull request. Else leave it be.

#### Table Of Contents

[Code of Conduct](#code-of-conduct)

[TL;DR](#tldr)

[What should I know before beginning?](#what-should-i-know-before-beginning)
  * [Dependencies](#dependencies)
  * [Design Decisions](#design-decisions)

[How can I contribute?](#how-can-i-contribute)
  * [Reporting Bugs](#reporting-bugs)
  * [Before Submitting a Bug Report](#before-submitting-a-bug-report)
  * [Suggesting Features](#suggesting-features)
  * [Pull Requests](#pull-requests)

[Styleguides](#styleguides)
  * [Git Commit Messages](#git-commit-messages)
  * [Documentation Styleguide](#documentation-styleguide)


## Code of Conduct
Everyone who particpates and or works on this project, is bound by the [Paper Code of Conduct](CODE_OF_CONDUCT.md). You will be expected to uphold the code. Please report anything unacceptable to [CaptainSly.theonly@gmail.com](mailto:captainsly.theonly@gmail.com). In the subject of the email, please put Paper or else I won't respond

## TLDR

> **Note:**  Please do not file an issue to ask a question. They will be ignored. If you have a question, send it to the email in the code of conduct section. THE SUBJECT MUST BE PAPER OR IT WILL BE IGNORED.

Basically if you want to contribute, don't be stupid. Don't change things that have no need to be changed, and if you do, don't remove anything. Comment it out. Always comment out rather than fully remove any code.

## What should I know before beginning?

### Dependencies

TODO

### Design Decisions

A lot of the code is sadly really messy. Things are being extended when they probably shouldn't be, and there is probably a lot of redundancy going on. If you see any code that looks like this, feel free to change it, either to make it look a bit more readable, or to perform better.


## How can I contribute?

### Reporting Bugs
We'll walk you through on writing a bug report for Paper. Please follow the guidelines and everything will go smoothly. Thank you.

Before making a bug report, I've made a handy dandy checklist that you can go through to decide if you should make a report. As of right now, The engine is not creating error logs, though this will be updated when that has been implemented.

When filling out an issue, please use [the required template](ISSUE_TEMPLATE.md), as the information it asks for helps resolve issues faster.

> **NOTE:** If you find a **Closed** issue that seems like it is the same thing that you're experiencing, open a new issue and include a link to the original issue in the body of your new one.

### Before Submitting a Bug Report

This check list will help you decide if a bug report is warranted.

**Did You Check the issues?** If there already is an issue like yours that hasn't been closed, and you have more information, leave a descriptive comment.

**Did You Check The Asset Folder?** If the asset folder isn't set to a source folder ***(at least in eclipse)*** it'll crash on startup with a null pointer exception. Make the asset folder a source folder inside the Core project and everything will work.

**Do NOT report org.lwjgl.LWJGLUTILS$3 errors:** When the project is started up you'll see a bunch of org.lwjgl.LWJGLUTILS$3 errors, ignore them. It's most likely because the default JDK being used is Java 10

#### How do I Submit A (Good) Bug Report?

Bugs are tracked as [Github Issues](https://guides.github.com/features/issues/). After you've found a bug, create an issue in the main branch and provide the following information by filling in [the template](ISSUE_TEMPLATE.md)

Explain the problem and include additional details that will help resolve the issue.

* **Use a clear and descriptive title** for the issue to identify the problem.

* **Describe the exact steps which will reproduce the problem** in as many details as humanly possible. If you're able to give a 3 page essay on what happened, do so. Start by explaining each step you went through that made the bug happen.

* **Don't just say what you did, Explain how you did it. And why**. For example, if you tried to collide with an object, tell me what object was being collided with, and in what way they collided. Use explicit detail.

* **Provide specific examples to demonstrate the steps**. Include links, images, videos, what have you. If it helps get your problem across to do so. As long as it's concise and easily gets the point across.

* **Describe the behavior you observed after following the steps** and point out what exactly is the problem with the behavior.

* **Explain which behavior you expected to see instead and why**.

* **If you're reporting that the Engine crashed**, include a crash report or whatever the console outputs. A stack trace will be found inside the Desktop folder called ***Engine.log***

* **If the problem is related to performance and memory**, use a profiler and send the data back.

* **If the problem wasn't triggered by a specific action**, describe what you were doing before the problem happened and share more information using the guidelines below.

Provide more context by answering these questions.

* **Can you reproduce the problem reliably?**
* **Did the problem start happening after a certain commit?**
* If the problem is with files and IO, **does the problem happen with all files, or is only certain ones?**

Include details about your configuration and environment:

* **Which version of the Engine are you using?** You can get the exact version by starting the Editor and going to the about section in the menu bar
* **What's the name and version of the OS you're using?**
* **Did you run the engine in a virtual machine?** If so, which VM software and what OS versions are being used for both the HOST and the GUEST
* **Which keyboard layout are you using?** Are you using US layout or some other?
* **Is the problem with running a script?** If so, what was the script you were trying to run? Did you use any dependencies? Include everything that script requires to run.

### Suggesting Features

This section guides you through submitting a feature, including completely new features and minor improvements to existing functionality. Following these guidelines helps maintainers and the community understand your suggestion :pencil: and find related suggestions :mag_right:

Before creating feature suggestions, please check previous suggestions as you might find out that you don't need to create one. When creating a feature suggestion, please [include as many details as possible](#how-do-i-submit-a-good-feature-suggestion). Fill in [the template](ISSUE_TEMPLATE.md), including the steps that you imagine you would take if the feature you're requesting existed.

#### How Do I Submit A (Good) Feature Suggestion?

Feature suggestions are tracked as [Github Issues](https://guides.github.com/features/issues/). Please provide the following information:

* **Use a clear and descriptive title** for the issue to identify the suggestion
* **Provide a step-by-step description of the suggested feature** in as many details as possible
* **Provide specific examples to demonstrate the steps**. Include copy/pastable snippets which you use in those examples, as [Markdown Code blocks](https://help.github.com/articles/markdown-basics/#multiple-lines).
* **Describe the current behavior** and **explain which behavior you expected to see instead** and why.
* **Include screenshots and animated GIFs.** which can help demonstrate the point of the feature. You can use [this tool](https://www.cockos.com/licecap/) to record GIFs on macOS and Windows, and [this tool](https://github.com/colinkeenan/silentcast) or [this tool](https://github.com/GNOME/byzanz) on Linux.
* **Explain why this feature would be useful** to most users and isn't something that can or should be implemented as a main part of the engine.
* **List some other engines/editors where this feature exists**
* **Specify the name and version of the OS you're using.**

### Your First Code Contribution

Unsure where to start? Everything look like a jumbled mess of cables and wires that need to be sifted through?

If there is an issue, see if you can fix said issue.

If an issue is marked as `help-wanted` or `urgent` then see if any contribution there can help.

### Pull Requests

The process described here has several goals:

  - Maintain Paper's quality
  - Fix problems that are important to users
  - Engage the community in working towards the best possible future for the engine
  - Enable a sustainable system for the Engine's maintainers to review contributions

  Please follow these steps to have your contribution considered by the maintainers

  1. Follow all instructions in [the template](PULL_REQUEST_TEMPLATE.md)
  2. Follow the [styleguides](#styleguides)
  3. After you submit your pull request, verify that all [status_checks](https://help.github.com/articles/about-status-checks/) are passing. <details><summary>What if the status checks are failing?</summary>If a status check is failing, and you believe that the failure is unrelated to your change, please leave a comment on the pull request explaining why you believe the failure is unrelated. A maintainer will re-run the status check for you. If we conclude that the failure was a false positive, then we will open an issue to track that problem with our status check suite.</details>

While the prerequisites above must be satisfied prior to having your pull request reviewed, the reviewer(s) may ask you to complete additional design work, tests, or other changes before your pull request can be ultimately accepted.

## Styleguides

### Git Commit Messages

* Use the present tense ("Add feature" not "Added feature")
* Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
* Limit the first line to 72 characters or less
* Reference issues and pull requests liberally after the first line
* When only changing documentation, include `[docs]` in the commit title
* Consider starting the commit message with an applicable emoji:
    * :art: `:art:` when improving the format/structure of the code
    * :racehorse: `:racehorse:` when improving performance
    * :non-potable_water: `:non-potable_water:` when plugging memory leaks
    * :memo: `:memo:` when writing docs
    * :penguin: `:penguin:` when fixing something on Linux
    * :apple: `:apple:` when fixing something on macOS
    * :checkered_flag: `:checkered_flag:` when fixing something on Windows
    * :bug: `:bug:` when fixing a bug
    * :fire: `:fire:` when removing code or files
    * :green_heart: `:green_heart:` when fixing the CI build
    * :white_check_mark: `:white_check_mark:` when adding tests
    * :lock: `:lock:` when dealing with security
    * :arrow_up: `:arrow_up:` when upgrading dependencies
    * :arrow_down: `:arrow_down:` when downgrading dependencies
    * :shirt: `:shirt:` when removing linter warnings

### Documentation Styleguide

* Use [Markdown](https://daringfireball.net/projects/markdown).
* Reference methods and classes in markdown with the custom `{}` notation:
    * Reference classes with `{ClassName}`
    * Reference instance methods with `{ClassName::methodName}`
    * Reference class methods with `{ClassName.methodName}`
