(ns aoc2020.day4
  (:require [aoc2020.utils :as utils]
            [clojure.string :as string]))

(def input
  (->> "day4"
       (utils/lines-from)))

(defn parse-line [line]
  (->> (string/split line #"\s")
       (mapcat #(string/split % #"\:"))
       (partition 2)
       (map vec)
       (into {})))

(def passports
  (loop [[head & rest] input
         passport {}
         result []]
    (if (and (empty? head) (empty? rest))
      (conj result passport)
      (if (empty? head)
        (recur rest {} (conj result passport))
        (recur rest (merge passport (parse-line head)) result)))))

(defn mandatory-fields-present? [passport]
  (and
    (contains? passport "byr")
    (contains? passport "iyr")
    (contains? passport "eyr")
    (contains? passport "hgt")
    (contains? passport "hcl")
    (contains? passport "ecl")
    (contains? passport "pid")))

(def first-part
  (->> (filter mandatory-fields-present? passports)
       (count)))

(defn byr-ok? [passport]
  (let [val (utils/string->int (get passport "byr" -1))]
    (<= 1920 val 2002)))

(defn iyr-ok? [passport]
  (let [val (utils/string->int (get passport "iyr" -1))]
    (<= 2010 val 2020)))

(defn eyr-ok? [passport]
  (let [val (utils/string->int (get passport "eyr" -1))]
    (<= 2020 val 2030)))

(defn hgt-ok? [passport]
  (let [unit (apply str (take-last 2 (get passport "hgt")))]
    (if (or (= unit "cm") (= unit "in"))
      (let [val (utils/string->int (apply str (drop-last 2 (get passport "hgt"))))]
        (if (= "cm" unit)
          (<= 150 val 193)
          (<= 59 val 76)))
      false)))

(defn ecl-ok? [passport]
  (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} (get passport "ecl")))

(def PID #"^\d{9}$")
(defn pid-ok? [passport]
  (not (nil? (re-matches PID (get passport "pid")))))

(def HCL #"^#[0-9a-f]{6}$")
(defn hcl-ok? [passport]
  (not (nil? (re-matches HCL (get passport "hcl")))))

(def second-part
  (->> (filter
         (every-pred
           mandatory-fields-present?
           byr-ok?
           iyr-ok?
           eyr-ok?
           hgt-ok?
           ecl-ok?
           pid-ok?
           hcl-ok?)
         passports)
       (count)))

(println "Solution first part: " first-part)
(println "Solution second part: " second-part)