(ns aoc2020.day7
  (:require [aoc2020.utils :as utils]
            [clojure.string :as string]
            [clojure.set]))

(def input (-> "day7" (utils/lines-from)))

(def PATTERN #"(\d+)\s(\w+\s\w+) bag")

(defn capacity [[_ amount bag]] { bag amount })
(defn grab-capacity [line] (->> (map capacity (re-seq PATTERN line)) (apply merge)))
(defn grab-bag [line] (->> (string/split line #"\s") (take 2) (string/join " ")))
(defn capacity-by-bag [line] {(grab-bag line) (grab-capacity line)})
(def rules (->> (map capacity-by-bag input) (apply merge)))

(defn first-part [the-bag start]
  (loop [[bag & rest] [start]
         result #{}]
    (let [capacity (get rules bag)
          new-bags (set (keys capacity))]
      (cond
        (nil? bag) result
        (= bag the-bag) (recur (into rest new-bags) (conj result start))
        :else (recur (into rest new-bags) result)
        ))))

(println "Solution first part: "
  (->> (map #(first-part "shiny gold" %) (dissoc (remove #{"shiny gold"} (keys rules))))
       (apply clojure.set/union)
       (count)))