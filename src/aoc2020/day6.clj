(ns aoc2020.day6
  (:require [aoc2020.utils :as utils]
            [clojure.set]))

(def input (->> "day6" (utils/lines-from)))

(def group-answers
  (loop [[line & rest] input
         current-group []
         groups []]
    (let [person-answers (seq line)]
      (cond
        (nil? line) (conj groups current-group)
        (nil? person-answers) (recur rest [] (conj groups current-group))
        :else (recur rest (conj current-group person-answers) groups)))))

(println "Solution first part: "
         (->>
              (map flatten group-answers)
              (map set)
              (map count)
              (apply +)))

(println "Solution second part: "
         (->>
           (map #(map set %) group-answers)
           (map #(apply clojure.set/intersection %))
           (map count)
           (apply +)))