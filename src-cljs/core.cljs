(ns cheshire-cat.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [clojure.browser.repl :as repl]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [enfocus.core :as ef]
            [enfocus.events :as ev]
            [enfocus.effects :as ee]))

(defn say-goodbye []
  (ef/at "#cat-name" (ee/fade-out 500))
  (ef/at "#button1" (ee/fade-out 500))
  (ef/at "#status" (ee/fade-out 5000)))

(defn ^:export init []
  (go
    (let [response (<! (http/get "/cheshire-cat"))
          body   (-> (:body response) (js/JSON.parse) (js->clj))]
         (ef/at "#cat-name" (ef/content (get body "name"))
                "#status" (ef/do->
                            (ef/content (get body "status"))
                            (ef/set-style :font-size "500%")))
         (ef/at "#button1" (ev/listen
                             :click
                             #(say-goodbye))))))
