package org.ergoplatform.dex.protocol.amm

import org.ergoplatform.dex.protocol.ErgoTreeSerializer
import org.ergoplatform.dex.protocol.amm.AMMType.{N2T_CFMM, T2T_CFMM}
import org.ergoplatform.ergo.SErgoTree
import sigmastate.Values.ErgoTree

trait AMMContracts[CT <: AMMType] {

  def pool: ErgoTree
}

object AMMContracts {

  implicit val T2TCFMMContracts: AMMContracts[T2T_CFMM] =
    new AMMContracts[T2T_CFMM] {

      def pool: ErgoTree =
        ErgoTreeSerializer.default.deserialize(
          SErgoTree.unsafeFromString(
            "19a9030f040004020402040404040406040605feffffffffffffffff0105feffffffffffffffff01050004d00f04000400050005" +
            "00d81ad601b2a5730000d602e4c6a70404d603db63087201d604db6308a7d605b27203730100d606b27204730200d607b2720373" +
            "0300d608b27204730400d609b27203730500d60ab27204730600d60b9973078c720602d60c999973088c720502720bd60d8c7208" +
            "02d60e998c720702720dd60f91720e7309d6108c720a02d6117e721006d6127e720e06d613998c7209027210d6147e720d06d615" +
            "730ad6167e721306d6177e720c06d6187e720b06d6199c72127218d61a9c72167218d1edededededed93c27201c2a793e4c67201" +
            "0404720292c17201c1a793b27203730b00b27204730c00938c7205018c720601ed938c7207018c720801938c7209018c720a0195" +
            "93720c730d95720f929c9c721172127e7202069c7ef07213069a9c72147e7215067e9c720e7e72020506929c9c721472167e7202" +
            "069c7ef0720e069a9c72117e7215067e9c72137e7202050695ed720f917213730e907217a19d721972149d721a7211ed9272199c" +
            "7217721492721a9c72177211"
          )
        )
    }

  implicit val N2TCFMMContracts: AMMContracts[N2T_CFMM] =
    new AMMContracts[N2T_CFMM] {

      def pool: ErgoTree =
        ErgoTreeSerializer.default.deserialize(
          SErgoTree.unsafeFromString(
            "1999030f0400040204020404040405feffffffffffffffff0105feffffffffffffffff01050004d00f0400040004060500050005" +
            "80dac409d819d601b2a5730000d602e4c6a70404d603db63087201d604db6308a7d605b27203730100d606b27204730200d607b2" +
            "7203730300d608b27204730400d6099973058c720602d60a999973068c7205027209d60bc17201d60cc1a7d60d99720b720cd60e" +
            "91720d7307d60f8c720802d6107e720f06d6117e720d06d612998c720702720fd6137e720c06d6147308d6157e721206d6167e72" +
            "0a06d6177e720906d6189c72117217d6199c72157217d1ededededededed93c27201c2a793e4c672010404720293b27203730900" +
            "b27204730a00938c7205018c720601938c7207018c72080193b17203730b9593720a730c95720e929c9c721072117e7202069c7e" +
            "f07212069a9c72137e7214067e9c720d7e72020506929c9c721372157e7202069c7ef0720d069a9c72107e7214067e9c72127e72" +
            "02050695ed720e917212730d907216a19d721872139d72197210ed9272189c721672139272199c7216721091720b730e"
          )
        )
    }
}