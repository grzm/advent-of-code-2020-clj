(ns com.grzm.advent-of-code.advent-2020.day-00-test
  (:require [com.grzm.advent-of-code.advent-2020.day-00 :as day-00]
            #?(:clj [clojure.test :as t :refer [deftest is]]
               :cljs [cljs.test :as t :refer [deftest is] :include-macros true])))

(deftest part-1
  (is (= [[0 3] [1 2] [2 5]] (day-00/part-1 {}))))

(comment
  (part-1)
  :end)

(deftest part-2
  (is (= "part-2" (day-00/part-2 {}))))
