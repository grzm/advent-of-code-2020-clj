(ns com.grzm.advent-of-code.advent-2020.day-02
  (:require
   [clojure.string :as str]
   [clojure.math.combinatorics :as combinatorics]
   [com.grzm.advent-of-code.advent-2020.util :as util]))

;; --- Day 2: Password Philosophy ---

;; Your flight departs in a few days from the coastal airport; the
;; easiest way down to the coast from here is via toboggan.

;; The shopkeeper at the North Pole Toboggan Rental Shop is having a bad
;; day. "Something's wrong with our computers; we can't log in!" You ask
;; if you can take a look.

;; Their password database seems to be a little corrupted: some of the
;; passwords wouldn't have been allowed by the Official Toboggan
;; Corporate Policy that was in effect when they were chosen.

;; To try to debug the problem, they have created a list (your puzzle
;; input) of passwords (according to the corrupted database) and the
;; corporate policy when that password was set.

;; For example, suppose you have the following list:

;; 1-3 a: abcde
;; 1-3 b: cdefg
;; 2-9 c: ccccccccc

;; Each line gives the password policy and then the password. The
;; password policy indicates the lowest and highest number of times a
;; given letter must appear for the password to be valid. For example,
;; 1-3 a means that the password must contain a at least 1 time and at
;; most 3 times.

;; In the above example, 2 passwords are valid. The middle password,
;; cdefg, is not; it contains no instances of b, but needs at least
;; 1. The first and third passwords are valid: they contain one a or
;; nine c, both within the limits of their respective policies.

;; How many passwords are valid according to their policies?

(def sample-input "1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc")

(comment
  (def input sample-input)
  :end)

(defn parse-line [line]
  (let [[bounds char password] (str/split line #"\s+")
        [min max] (str/split bounds #"-")]
    {:char (first char)
     :min (util/parse-int min)
     :max (util/parse-int max)
     :pw password}))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map (comp
              parse-line))))

(defn valid? [{:keys [min max char pw]}]
  (let [c (->> pw seq (filter #{char}) count)]
    (<= min c max)))

(comment

  (->> input
       parse-input
       (filter valid?)
       count)
  
  :end)

(defn part-1 [{}]
  (->> (util/slurp-resource "data/day-02")
       parse-input
       (filter valid?)
       count))

(comment
  (part-1 nil) ;; 607
  :end)

;; --- Part Two ---

;; While it appears you validated the passwords correctly, they don't
;; seem to be what the Official Toboggan Corporate Authentication System
;; is expecting.

;; The shopkeeper suddenly realizes that he just accidentally explained
;; the password policy rules from his old job at the sled rental place
;; down the street! The Official Toboggan Corporate Policy actually works
;; a little differently.

;; Each policy actually describes two positions in the password, where 1
;; means the first character, 2 means the second character, and so
;; on. (Be careful; Toboggan Corporate Policies have no concept of "index
;; zero"!) Exactly one of these positions must contain the given
;; letter. Other occurrences of the letter are irrelevant for the
;; purposes of policy enforcement.

;; Given the same example list from above:

;; 1-3 a: abcde is valid: position 1 contains a and position 3 does not.
;; 1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
;; 2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
;; How many passwords are valid according to the new interpretation of
;; the policies?

(defn part-2-parse-line [line]
  (let [[positions char password] (str/split line #"\s+")]
    {:char (first char)
     :positions (->> (str/split positions #"-") (mapv util/parse-int))
     :pw password}))

(defn part-2-valid? [{:keys [char positions pw]}]
  (->> positions
       (map (fn [p] (= char (get pw (dec p)))))
       (filter identity)
       count
       dec
       zero?))


(comment

  (get "foo" 1)

  :end)

(defn part-2* [input]
    (->> input
       str/split-lines
       (map part-2-parse-line)
       (map part-2-valid?)
       (filter identity)
       count))

(defn part-2 [_]
  (part-2* (util/slurp-resource "data/day-02")))

(comment
  (part-2 nil) ;; 321
  :end)

(defn run [{:keys [part]}]
  (case part
    :part-1 (println (part-1 {}))
    :part-2 (println (part-2 {}))))

(defn -main [& args]
  (when-let [part (first args)]
    (run {:part (keyword part)})))
