using System;
using System.Collections.Generic;
using System.Xml.Serialization;

namespace BPMN.ELEMENTS
{

    public static class Const
    {
        public const string bpmn2 = @"http://www.omg.org/spec/BPMN/20100524/MODEL";
    }

    [XmlRoot(Namespace = Const.bpmn2)]
    public class definitions
    {
        [XmlAttribute("id")]
        public string id { get; set; }
        public process process { get; set; }
    }


    [XmlType(Namespace = Const.bpmn2)]
    public class process
    {
        [XmlAttribute]
        public string id { get; set; }
        [XmlAttribute]
        public string name { get; set; }
        [XmlAttribute]
        public Boolean isExecutable { get; set; }

        [XmlElement(Type = typeof(startEvent))]
        [XmlElement(Type = typeof(exclusiveGateway))]
        [XmlElement(Type = typeof(parallelGateway))]
        [XmlElement(Type = typeof(sequenceFlow))]
        public List<object> Elements { get; set; }

    }

    public class startEvent
    {
        [XmlAttribute]
        public string id { get; set; }
        [XmlAttribute]
        public string name { get; set; }
    }

    public class exclusiveGateway
    {
        [XmlAttribute]
        public string id { get; set; }
        [XmlAttribute]
        public string name { get; set; }


    }


    public class parallelGateway
    {
        [XmlAttribute]
        public string id { get; set; }
        [XmlAttribute]
        public string name { get; set; }


        [XmlElement(Type = typeof(incoming))]
        [XmlElement(Type = typeof(outgoing))]
        public List<object> Flowgoing { get; set; }
    }


    public class sequenceFlow
    {
        [XmlAttribute]
        public string id { get; set; }
        [XmlAttribute]
        public string name { get; set; }

        [XmlAttribute]
        public string sourceRef { get; set; }
        [XmlAttribute]
        public string targetRef { get; set; }

    }


    public class incoming
    {
        [XmlText]
        public string name { get; set; }
    }

    public class outgoing
    {
        [XmlText]
        public string name { get; set; }
    }


}