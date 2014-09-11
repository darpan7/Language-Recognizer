#lang racket

(require math/statistics
         math/special-functions)

;;; Step 1 - Load the languages table.

(define language-regexp #px"([A-Za-z-]*).dat$")

;;; (load-languages)
;;; -> (hash/c string? (hash/c exact-nonnegative-integer?
;;;                            (list/c (>/c 0.0) (>=/c 0.0))))
(define (load-languages)
  ;; (load-language language-path)
  ;; -> (hash/c exact-nonnegative-integer? (list/c (>/c 0.0) (>=/c 0.0)))
  ;;   language-path : path-string?
  (define (load-language language-path)
    (define language-table (make-hash))
    (define port (open-input-file language-path))
    (for ((line (in-lines port)))
      (define tokens (regexp-split #px"," line))
      (define code-point (string->number (first tokens)))
      (define mean (string->number (second tokens)))
      (define stddev (string->number (third tokens)))
      (hash-set! language-table code-point (list mean stddev)))
    (close-input-port port)
    language-table)
  ;; languages-table : hash?
  (define languages-table (make-hash))
  ;; Load all of the languages and add them to the languages table.
  (for ((language-path (in-directory "Training Set"))
        #:when (regexp-match? language-regexp language-path))
    (define language (second (regexp-match language-regexp language-path)))
    (hash-set! languages-table language (load-language language-path)))
  ;; Return the languages table.
  languages-table)

;;; Step 2 - Load an unknown language sample

;;; (load-sample sample-path)
;;; -> (hash/c exact-nonnegative-integer? (real-in 0.0 1.0))
;;;   sample-path : path-string?
(define (load-sample sample-path)
  (define sample-table (make-hash))
  (define port (open-input-file sample-path))
  (define sample (read-line port 'any))
  (define total-count (string->number (read-line port 'any)))
  (for ((line (in-lines port)))
    (define tokens (regexp-split #px"," line))
    (define code-point (string->number (first tokens)))
    (define count (string->number (second tokens)))
    (hash-set! sample-table code-point (/ count total-count)))
  (close-input-port port)
  (values sample sample-table))

;;; Step 3 - Classify an unknown language sample.

(define (classify-sample sample sample-table languages-table)
  (define (compute-score sample-table language language-table)
    (define score-stats empty-statistics)
    (for (((code-point f) (in-hash sample-table)))
      (define-values (mean stddev)
        (apply values (hash-ref language-table code-point '(0.0 0.0))))
      (define z
        (if (= stddev 0.0)
            100.0
            (/ (abs (- f mean)) stddev)))
      ;(printf "~a: f=~s, z=~a~n" code-point f z))
      ;(define s z)
      (define s (erf (/ z (sqrt 2.0))))
      (set! score-stats (update-statistics score-stats s)))
      (printf "~a: score = ~a~n" language (statistics-mean score-stats)))
  (printf "~a~n" sample)
  (for (((language language-table) (in-hash languages-table)))
    (compute-score sample-table language language-table))
  (printf "---~n~n"))

;;; Step 4 - Class all of the unknown language samples.

(define sample-regexp #px"Sample_([0-9]*).txt$")

(define (main)
  (define languages-table (load-languages))
  (for ((sample-path (in-directory "Samples"))
        #:when (regexp-match? sample-regexp sample-path))
    (printf "Sample file: ~a~n" sample-path)
    (define-values (sample sample-table) (load-sample sample-path))
    (classify-sample sample sample-table languages-table)))

(main)
