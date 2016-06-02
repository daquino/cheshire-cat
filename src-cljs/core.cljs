(ns cheshire-cat.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [clojure.browser.repl :as repl]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [enfocus.core :as ef]))

(defn ^:export init []
  (go
    (let [response (<! (http/get "/cheshire-cat"))
          body   (-> (:body response) (js/JSON.parse) (js->clj))]
         (ef/at "#cat-name" (ef/content (get body "name")))
         (ef/at "#status" (ef/content (get body "status"))))))