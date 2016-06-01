(ns cheshire-cat.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [clojure.browser.repl :as repl]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [enfocus.core :as ef]))

(defn ^:export init []
  (go
    (let [response (<! (http/get "/cheshire-cat"))
          body  (:body response)]
         (js/console.log (str "Body is: " body))
         (js/console.log (str "Name is: " (:name body)))
         (ef/at "#cat-name" (ef/content (:name body)))
         (ef/at "#status" (ef/content (:status body))))))