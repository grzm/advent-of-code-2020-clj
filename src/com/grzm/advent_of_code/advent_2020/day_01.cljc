(ns com.grzm.advent-of-code.advent-2020.day-01
  (:require
   [clojure.string :as str]
   [clojure.math.combinatorics :as combinatorics]
   [com.grzm.advent-of-code.advent-2020.util :as util]))

;; --- Day 1: Report Repair ---

;; After saving Christmas five years in a row, you've decided to take
;; a vacation at a nice resort on a tropical island. Surely, Christmas
;; will go on without you.

;; The tropical island has its own currency and is entirely
;; cash-only. The gold coins used there have a little picture of a
;; starfish; the locals just call them stars. None of the currency
;; exchanges seem to have heard of them, but somehow, you'll need to
;; find fifty of these coins by the time you arrive so you can pay the
;; deposit on your room.

;; To save your vacation, you need to get all fifty stars by December
;; 25th.

;; Collect stars by solving puzzles. Two puzzles will be made
;; available on each day in the Advent calendar; the second puzzle is
;; unlocked when you complete the first. Each puzzle grants one
;; star. Good luck!

;; Before you leave, the Elves in accounting just need you to fix your
;; expense report (your puzzle input); apparently, something isn't
;; quite adding up.

;; Specifically, they need you to find the two entries that sum to
;; 2020 and then multiply those two numbers together.

;; For example, suppose your expense report contained the following:

;; 1721 979 366 299 675 1456 In this list, the two entries that sum to
;; 2020 are 1721 and 299. Multiplying them together produces 1721 *
;; 299 = 514579, so the correct answer is 514579.

(def sample-input "1721
979
366
299
675
1456")

;; seems like there's likely an efficient way to do this with sorting

;; naive approach
;; take first, and try it with each of the rest
;; if not, drop it, repeat with the rest

;; https://codereview.stackexchange.com/questions/135737/generate-all-permutations-in-clojure#comment406256_137860
(defn tails [coll] (take-while seq (iterate rest coll)))

(defn pairs [[h & r]]
  (map (partial vector h) r))

;; [ a b c d ]
;; [ a ] [ b c d ]
;; [ b ] [ c d ]
;; [ c ] [ d ]

(defn parse-input [input]
  (->> input
       str/split-lines
       (map (comp util/parse-int
                  #(str/trim %)))))

(defn part-1 [input]
  (->> input
       str/split-lines
       (map (comp util/parse-int
                  #(str/trim %)))
       sort
       tails
       (mapcat pairs)
       (some (fn [[x y :as pair]] (when (= (+ x y) 2020)
                                    pair)))
       (apply *)))

(def input (util/slurp-resource "data/day-01"))

(comment

  (part-1 sample-input) ;; => [299 1721]

  input
  (part-1 input) ;; [319 1701]
  (apply * (part-1 input)) ;; 542619
  (parse-input input)

  (-> (parse-input input)
      (combinatorics/combinations 3)
      (->> (some (fn [combo]
                   (when (= 2020 (apply + combo))
                     combo)))
           (apply *))) ;; 32858450

  ;;   --- Part Two ---

  ;; The Elves in accounting are thankful for your help; one of them
  ;; even offers you a starfish coin they had left over from a past
  ;; vacation. They offer you a second one if you can find three
  ;; numbers in your expense report that meet the same criteria.

  ;; Using the above example again, the three entries that sum to 2020
  ;; are 979, 366, and 675. Multiplying them together produces the
  ;; answer, 241861950.

  ;; In your expense report, what is the product of the three entries
  ;; that sum to 2020?

  :end)

(defn part-2 [input]
  (-> (parse-input input)
      (combinatorics/combinations 3)
      (->> (some (fn [combo]
                   (when (= 2020 (apply + combo))
                     combo)))
           (apply *))))

(defn -main [& args]
  (case (first args)
    "part-1" (println (part-1 input))
    "part-2" (println (part-2 input))))
