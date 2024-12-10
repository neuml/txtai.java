<p align="center">
    <img src="https://raw.githubusercontent.com/neuml/txtai/master/logo.png"/>
</p>

<p align="center">
    <b>Java client for txtai</b>
</p>

<p align="center">
    <a href="https://github.com/neuml/txtai.java/releases">
        <img src="https://img.shields.io/github/release/neuml/txtai.java.svg?style=flat&color=success" alt="Version"/>
    </a>
    <a href="https://github.com/neuml/txtai.java/releases">
        <img src="https://img.shields.io/github/release-date/neuml/txtai.java.svg?style=flat&color=blue" alt="GitHub Release Date"/>
    </a>
    <a href="https://github.com/neuml/txtai.java/issues">
        <img src="https://img.shields.io/github/issues/neuml/txtai.java.svg?style=flat&color=success" alt="GitHub Issues"/>
    </a>
    <a href="https://github.com/neuml/txtai.java">
        <img src="https://img.shields.io/github/last-commit/neuml/txtai.java.svg?style=flat&color=blue" alt="GitHub Last Commit"/>
    </a>
</p>

[txtai](https://github.com/neuml/txtai) is an all-in-one embeddings database for semantic search, LLM orchestration and language model workflows.

This repository contains Java bindings for the txtai API.

## Installation

Jitpack is the recommended way to integrate txtai with Java. See [this link](https://jitpack.io/) for how to install JitPack.

The following is an example adding txtai to a project's `build.gradle` file. The same attributes can be ported to other build systems per the JitPack link above.

```gradle
implementation 'com.github.neuml:txtai.java:v8.1.0'
```

txtai can also be manually built from GitHub.

```bash
git clone https://github.com/neuml/txtai.java
cd txtai.java
./gradlew jar
```

The JAR will be available in ./build/libs

## Examples
The examples directory has a series of examples that give an overview of txtai. See the list of examples below.

| Example     |      Description      |
|:----------|:-------------|
| [Introducing txtai](https://github.com/neuml/txtai.java/blob/master/examples/src/main/java/EmbeddingsDemo.java) | Overview of the functionality provided by txtai |
| [Extractive QA with txtai](https://github.com/neuml/txtai.java/blob/master/examples/src/main/java/ExtractorDemo.java) | Extractive question-answering with txtai |
| [Labeling with zero-shot classification](https://github.com/neuml/txtai.java/blob/master/examples/src/main/java/LabelsDemo.java) | Labeling with zero-shot classification |
| [Pipelines and workflows](https://github.com/neuml/txtai.java/blob/master/examples/src/main/java/PipelinesDemo.java) | Pipelines and workflows |

txtai.java connects to a txtai api instance. See [this link](https://neuml.github.io/txtai/api/) for details on how to start a new api instance.

Once an api instance is running, do the following to run the examples.

```
git clone https://github.com/neuml/txtai.java
cd txtai.java/examples
../gradlew embeddings|extractor|labels|pipelines
```
