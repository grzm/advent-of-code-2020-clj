.PHONY: cljs test test-cljs test-cljs-watch test-watch

CLJS_BUILDS := $(shell bin/list-cljs-builds)

cljs :
	shadow-cljs compile $(CLJS_BUILDS)

test :
	clojure -M:test

test-watch :
	clojure -M:test --watch

test-cljs :
	shadow-cljs compile test

test-cljs-watch :
	shadow-cljs watch test 

