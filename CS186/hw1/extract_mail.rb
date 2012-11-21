#!/usr/bin/env ruby

require 'mail'
require 'csv'
require 'strscan'
require 'find'

from = nil
message_id = nil
body = nil

CSV.open('mail.csv', 'ab') do |csv_mail|
CSV.open('tokens.csv', 'ab') do |csv_tokens|
  Find.find(ARGV[0]) do |f|
    if f.match(/\.txt\z/)    
      mail = Mail.read(f)
      from = mail.from
      exit if from.nil?
      message_id = mail.message_id
      body = mail.body
      csv_mail << [ f, from, mail.to, mail.cc, mail.subject, mail.date, message_id, body ]
      ss = StringScanner.new(body.to_s.strip)
      until ss.eos?
        word = ss.scan(/^[a-zA-Z]+/)
        word = word.to_s
        if not word.empty?
          csv_tokens << [ message_id, word.downcase ]
        end
        word = ss.scan(/^[^a-zA-Z]+/)
      end
    end
  end
end
end
