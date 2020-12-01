(ns aoc2020.utils
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn read-resource [filename]
  (-> (io/resource filename)
      (slurp)))

(defn lines-from [filename]
  (-> (read-resource filename)
      (string/split-lines)))

(defn string->int [s] (Integer/parseInt s))
