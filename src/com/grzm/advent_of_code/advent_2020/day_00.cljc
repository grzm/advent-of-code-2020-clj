(ns com.grzm.advent-of-code.advent-2020.day-00
  (:require
   [clojure.string :as str]
   [com.grzm.advent-of-code.advent-2020.util :as util]))

(def input (util/slurp-resource "data/day-00"))

(defn part-1 [_]
  (->> (str/split input #"\n")
       (remove #(str/blank? %))
       (map (comp vec
                  #(map util/parse-int %)
                  #(str/split % #":\s+")
                  str/trim))
       (take 3)
       vec))

(defn part-2 [_]
  "part-2")

(defn run [{:keys [part]}]
  (case part
    :part-1 (println (part-1 input))
    :part-2 (println (part-2 input))))

;; cljs and bb
(defn -main [& args]
  (when-let [part (first args)]
    (run {:part (keyword part)})))
