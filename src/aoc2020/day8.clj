(ns aoc2020.day8
  (:require [aoc2020.utils :as utils]
            [clojure.string :as string]))

(def input
  (->> "day8"
       (utils/lines-from)
       (map #(string/split % #"\s"))
       (map (fn [[a b]] [a (utils/string->int b)]))
       (map-indexed hash-map)
       (apply merge)))

(defn traverse [graph]
  (loop [idx 0
         acc 0
         visited #{}]
    (cond
      (contains? visited idx) {:cause :loop :acc acc}
      (and (nil? (get graph idx)) (= idx (count graph))) {:acc acc}
      (nil? (get graph idx)) {:cause :boom :acc acc}
      :else (let [[op val] (get graph idx)]
              (case op
                "nop" (recur (inc idx) acc (conj visited idx))
                "acc" (recur (inc idx) (+ acc val) (conj visited idx))
                "jmp" (recur (+ idx val) acc (conj visited idx)))))))

(println "Solution first part: " (:acc (traverse input)))

(def nop-and-jmp (filter (fn [[_ [op _]]] (contains? #{"jmp" "nop"} op)) input))

(def part-two
  (loop [[[idx [op _]] & rest] nop-and-jmp]
    (let [[_ val] (get input idx)
          flipped (if (= "jmp" op) "nop" "jmp")
          result (traverse (assoc input idx [flipped val]))]
      (if (= :loop (:cause result))
        (recur rest)
        (:acc result)))))

(println "Solution second part:" part-two)