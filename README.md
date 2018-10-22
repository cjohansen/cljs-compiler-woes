# ClojureScript compiler woes

Goal: I have an existing ClojureScript app that runs in the browser. I want to
use some of its namespaces in a Node program. I have a program that runs fine in
figwheel, but I am running into problems trying to build a standalone bundle
with optimizations other than `:none`. This repo reproduces the problem in small
scale.

As far as I can tell it seems that `cljsjs` dependencies (either in general, or
`cljsjs/moment` specifically) does not play nice with the compiler. When I use
any optimization other than `none`, I get `"Error: Cannot find module
'../moment'"`.

1. Figwheel REPL in browser, `:optimizations :none` works
   ```sh
   clojure -A:dev -A:browser-repl
   ```
2. cljs.main browser build, `:optimizations :simple` works
   ```sh
   clojure -A:dev -A:browser-build
   ```
3. Figwheel REPL in node, `:optimizations :none` works, with warnings (see below)
   ```sh
   clojure -A:dev -A:node-repl
   ```
4. cljs.main node build, `:optimizations :simple` works
   ```sh
   clojure -A:dev -A:node-build-dev
   ```
5. cljs.main node build, `:optimizations :simple` **does not work** (see below)
   ```sh
   clojure -A:dev -A:node-build-prod
   ```

## Figwheel + node

When running with Figwheel and node, everything works as expected. If I add
`:main cljs-compiler-woes.core`, I see this error in the node log during
startup:

```
[Figwheel REPL] Error: Cannot find module '../moment'
[Figwheel REPL] Error loading file ../cljsjs/common/locale/nb.inc.js
```

However, requiring the namespace from the REPL produces the expected result.

## cljsbuild + node and simple optimizations

The node version produced by cljsbuild with any optimizations other than "none"
fails on startup with the same error as seen above:

```
Error: Cannot find module '../moment'
```

## cljsjs vs node deps

If I pull in moment as a node dependency, all the builds work as expected.
However, this isn't a readily available solution, as the code base I'm trying to
partially load into node is big, and I would like to avoid rewriting its
dependency management.

In the code-base where this problem originated, I tried to manually patch over
the moment issue, only to be served with a similar error for React (also from
cljsjs).
