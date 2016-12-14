package com.appliedscala.events

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.{JsValue, Json}


/**
  * Created by denis on 11/27/16.
  */
case class LogRecord(id: UUID, action: String,
                     data: JsValue, timestamp: DateTime) {
  def encode: String = {
    Json.toJson(this)(LogRecord.writes).toString()
  }
}

object LogRecord {
  val writes = Json.writes[LogRecord]
  val reads = Json.reads[LogRecord]
  def decode(str: String): Option[LogRecord] = {
    Json.parse(str).asOpt[LogRecord](reads)
  }
  def fromEvent(eventData: EventData): LogRecord = {
    LogRecord(UUID.randomUUID(), eventData.action,
      eventData.json, DateTime.now())
  }
}