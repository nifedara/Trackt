import { Button, Card, CardFooter, Image } from '@chakra-ui/react'
import React from 'react'

export const DestinationCard = ({ data }) => {
    return (
        <Card variant={"unstyled"}>
            <Image src={data.imageUrl} h="14rem" objectPosition={"center"} objectFit={"cover"} rounded={"md"} />
            <CardFooter mt={2}>
                <Button w={"full"}>{data.location}</Button>
            </CardFooter>
        </Card>
    )
}
