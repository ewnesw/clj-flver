(ns clj-flver.core
  (:require [clojure-watch.core :refer [start-watch]]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as string]))

(def paths (atom {}))

(defn watch-path
  "take a path and start watching the given path"
  [path]
  (start-watch [{:path path
               :event-types [:create :modify :delete]
               :bootstrap (fn [path] (println "Starting to watch " path))
               :callback (fn [event filename] (println "proc" event filename))
               :options {:recursive true}}]))

(defn add-path
  "add given path and return fn from watch-path to atom paths"
  [path]
  (swap! paths
         (fn [current-state]
           (assoc current-state path (watch-path path)))))

(defn it-path
  "iterate and apply add-path"
  [lpaths]
  (doseq [lpath lpaths]
          (add-path lpath)))

(def cli-options
  ;; An option with a required argument
  [["-p" "--paths [PATHS]" "Path list"
    :missing true
    :parse-fn #(read-string %)]
    ;;:validate [#(and (vector? %) (not (contains? (map string? %) false))) "arg must be an array of string"]]
   ["-h" "--help"]])

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn usage [options-summary]
  (->> ["This is my program. There are many like it, but this one is mine."
        ""
        "Usage: program-name [options] action"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  start    Start a new server"
        "  stop     Stop an existing server"
        "  status   Print a server's status"
        ""
        "Please refer to the manual page for more information."]
       (string/join \newline)))

(defn validate-args
  "Validate command line arguments. Either return a map indicating the program
  should exit (with an error message, and optional ok status), or a map
  indicating the action the program should take and the options provided."
  [args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) ; help => exit OK with usage summary
      {:exit-message (usage summary) :ok? true}
      errors ; errors => exit with description of errors
      {:exit-message (error-msg errors)}
      )))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{:keys [action options exit-message ok?]} (validate-args args)]
    (when exit-message
      (exit (if ok? 0 1) exit-message))))
  ;;(dorun (map println (it-path args))))
