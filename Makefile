.PHONY: cljs test test-cljs test-cljs-watch test-watch

CLJS_BUILDS := $(shell bin/list-cljs-builds)

cljs :
	clojure -M:shadow-cljs compile $(CLJS_BUILDS)

test :
	clojure -M:test

test-watch :
	clojure -M:test --watch

test-cljs :
	clojure -M:shadow-cljs compile test

test-cljs-watch :
	clojure -M:shadow-cljs watch test

