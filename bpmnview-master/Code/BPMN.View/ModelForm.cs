// 
// The MIT License (MIT)
//
// Copyright (c) 2017 Boris Zinchenko
// mail: info@caseagile.com
// web: http://www.caseagile.com
// code: https://github.com/bzinchenko/bpmnview
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace BPMN.View
{
  public partial class ModelForm : Form
  {
    private Model model;

    public ModelForm(Model mdl)
    {
      InitializeComponent();
      ctlElement.Init(mdl);
      model = mdl;
    }

    private void ElementsForm_Load(object sender, EventArgs e)
    {
      TreeNode node = ModelTree.AddElementToTree(treeElements, null, model.Root);
      if (node != null) node.Expand();
    }

    private void treeElements_AfterSelect(object sender, TreeViewEventArgs e)
    {
      ctlElement.Cleanup();
      TreeNode node = treeElements.SelectedNode;
      if(node != null && node.Tag  != null) 
      {
        Element elm = node.Tag as Element;
        ctlElement.ViewElement(elm);
      }
    }

  }
}
