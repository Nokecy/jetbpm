﻿using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Xml;
using System.Xml.Serialization;

namespace BPMNET.Bpmn
{
    [GeneratedCode("xsd", "4.0.30319.1")]
    [Serializable()]
    [DebuggerStepThrough()]
    [DesignerCategory("code")]
    [XmlType(Namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")]
    [XmlRoot("globalChoreographyTask", Namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", IsNullable = false)]
    public partial class tGlobalChoreographyTask : tChoreography
    {

        private XmlQualifiedName initiatingParticipantRefField;

            [XmlAttribute()]
        public XmlQualifiedName initiatingParticipantRef
        {
            get
            {
                return initiatingParticipantRefField;
            }
            set
            {
                initiatingParticipantRefField = value;
            }
        }
    }
}
