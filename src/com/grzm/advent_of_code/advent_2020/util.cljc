(ns com.grzm.advent-of-code.advent-2020.util
  (:require
   #?(:clj [clojure.java.io :as io])))

#?(:clj
   (defn parse-int [v]
     (Integer/parseInt v)))

#?(:cljs (defn parse-int [v]
           (js/parseInt v)))

#?(:clj
   (defn slurp-resource [s]
     (-> s
         (io/resource)
         (slurp))))

#?(:cljs
   (do
     (def fs (js/require "fs"))
     (def path (js/require "path"))
     (def process (js/require "process"))))

#?(:cljs
   (defn slurp-resource [s]
     (let [resources-dir (or process.env.RESOURCES_DIR "resources")
           file-path (path.join resources-dir s)]
       (fs.readFileSync file-path "utf8"))))
